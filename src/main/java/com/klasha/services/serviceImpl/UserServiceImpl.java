package com.klasha.services.serviceImpl;

import com.klasha.dtos.DeliveryDto;
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
        Optional<User> existingUser = findUser(loginDto.getEmail());
        User loggedInUser = (User) httpSession.getAttribute(existingUser.get().getEmail());
        if (loggedInUser != null) {
            return "Welcome Back";
        } else {
            httpSession.setAttribute(loginDto.getEmail(), existingUser.get());
            return "User login Successfully";
        }
    }

    @Override
    public String addLocation(DeliveryDto deliveryDto, HttpSession httpSession) {
        Delivery delivery = new Delivery();
        findUser(deliveryDto.getEmail());
        User loggedInUser = (User) httpSession.getAttribute(deliveryDto.getEmail());
        if (loggedInUser != null) {
            delivery.setAddress(deliveryDto.getAddress());
            delivery.setUser(loggedInUser);
            deliveryRepository.save(delivery);
            return "Location added Successfully";
        } else return USER_NOT_CURRENTLY_LOGGED_IN;
    }

    @Override
    public String updateLocation(long deliveryId, DeliveryDto deliveryDto, HttpSession httpSession) {
        Delivery existingDelivery = deliveryRepository.findById(deliveryId).orElseThrow(
                ()-> new CustomAppException("Resource not Found"));
        User loggedInUser = (User) httpSession.getAttribute(deliveryDto.getEmail());
        if (loggedInUser != null) {
            existingDelivery.setUser(loggedInUser);
            existingDelivery.setAddress(deliveryDto.getAddress());
            deliveryRepository.save(existingDelivery);
            return "Location updated successfully";
        } else return USER_NOT_CURRENTLY_LOGGED_IN;
    }

    @Override
    public String removeLocation(long deliveryId, HttpSession httpSession) {
        Delivery existingDelivery = deliveryRepository.findById(deliveryId).orElseThrow(
                ()-> new CustomAppException("Resource not Found"));
        User loggedInUser = (User) httpSession.getAttribute(existingDelivery.getUser().getEmail());
        if (loggedInUser != null) {
            deliveryRepository.delete(existingDelivery);
            return "Location removed successfully";
        }
        return USER_NOT_CURRENTLY_LOGGED_IN;
    }

    @Override
    public List<Delivery> viewLocations() {
        return (List<Delivery>) deliveryRepository.findAll();
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
