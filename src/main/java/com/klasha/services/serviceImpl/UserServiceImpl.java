package com.klasha.services.serviceImpl;

import com.klasha.dtos.LocationDto;
import com.klasha.dtos.LoginDto;
import com.klasha.dtos.SignupDto;
import com.klasha.exceptions.CustomAppException;
import com.klasha.exceptions.UserNotFoundException;
import com.klasha.models.Delivery;
import com.klasha.models.Location;
import com.klasha.models.User;
import com.klasha.repositories.DeliveryRepository;
import com.klasha.repositories.UserRepository;
import com.klasha.responses.DeliveryResponse;
import com.klasha.services.DeliveryService;
import com.klasha.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, DeliveryService {
    public static final String USER_NOT_CURRENTLY_LOGGED_IN = "User not currently logged in";
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;


    @Override
    public String register(SignupDto signupDto) {
        User user = new User();
        Optional<User> existingUser = userRepository.findUserByEmail(signupDto.getEmail());
        if (existingUser.isPresent()) {
            throw new CustomAppException("User already exist, Kindly login");
        }
        user.setEmail(signupDto.getEmail());
        user.setName(signupDto.getName());
        user.setPassword(signupDto.getPassword());
        userRepository.save(user);
        return "User Registered Successfully";
    }

    @Override
    public String login(LoginDto loginDto, HttpSession httpSession) {
        User user = userRepository.findUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new CustomAppException("Incorrect email or password"));
        httpSession.setAttribute("user", user);
        return "User login Successfully";
    }

    @Override
    public String addDeliveryLocation(String address, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (loggedInUser != null) {
            Delivery delivery = new Delivery();
            delivery.setAddress(address);
            delivery.setUser(loggedInUser);
            deliveryRepository.save(delivery);
            return "Location added Successfully";
        } else throw new CustomAppException(USER_NOT_CURRENTLY_LOGGED_IN);
    }

    @Override
    public String updateDeliveryLocation(long deliveryId, String address, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (loggedInUser != null) {
            Delivery existingDelivery = deliveryRepository.findByIdAndUser(deliveryId, loggedInUser).orElseThrow(
                    ()-> new CustomAppException("Resource not Found"));
            existingDelivery.setAddress(address);
            deliveryRepository.save(existingDelivery);
            return "Location updated successfully";
        } else throw new CustomAppException(USER_NOT_CURRENTLY_LOGGED_IN);
    }

    @Override
    public String removeLocation(long deliveryId, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (loggedInUser != null) {
            Delivery existingDelivery = deliveryRepository.findByIdAndUser(deliveryId, loggedInUser).orElseThrow(
                    ()-> new CustomAppException("Resource not Found"));
            deliveryRepository.delete(existingDelivery);
            return "Location removed successfully";
        } else throw new CustomAppException(USER_NOT_CURRENTLY_LOGGED_IN);
    }

    @Override
    public List<Delivery> viewLocations() {
        return deliveryRepository.findAll();
    }

    @Override
    public DeliveryResponse optimalRouteAndDeliveryCost(LocationDto locationDto) {
        Location origin = locationDto.getOrigin();
        Location destination = locationDto.getDestination();
        Long x1 = origin.getX_coordinate();
        Long y1 = origin.getY_coordinate();
        Long x2 = destination.getX_coordinate();
        Long y2 = destination.getY_coordinate();
        double x_vector = Math.pow((x2 - x1), 2);
        double y_vector = Math.pow((y2 - y1), 2);
        double x_y_vector = x_vector + y_vector;
        double distance = Math.sqrt(x_y_vector);

        DeliveryResponse response = new DeliveryResponse();
        response.setOptimalRoute(distance);
        double amount = getCost(distance);
        Locale us = new Locale("en", "US");
        NumberFormat formatAmount = NumberFormat.getCurrencyInstance(us);
        response.setCostOfDelivery(formatAmount.format(amount));
        return response;
    }

    private double distanceInKilometres(double distance) {
        //assume the distance is in metres
        return distance / 1000;
    }

    private double getCost(double distance) {
        double distanceInKilometre = distanceInKilometres(distance);
        //assume $1.00 per distance
        return distanceInKilometre * 1;
    }

    private Optional<User> findUser(String email) {
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) {
            return existingUser;
        } else throw new UserNotFoundException("User not found");
    }
}
