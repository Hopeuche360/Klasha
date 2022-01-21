package com.klasha.services.serviceImpl;

import com.klasha.dtos.DeliveryDto;
import com.klasha.dtos.LoginDto;
import com.klasha.dtos.SignupDto;
import com.klasha.exceptions.CustomAppException;
import com.klasha.exceptions.UserNotFoundException;
import com.klasha.models.Delivery;
import com.klasha.models.User;
import com.klasha.repositories.DeliveryRepository;
import com.klasha.repositories.UserRepository;
import com.klasha.services.DeliveryService;
import com.klasha.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, DeliveryService {
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
        Optional<User> existingUser = userRepository.findUserByEmail(loginDto.getEmail());
        if (existingUser.isPresent()) {
            User loggedInUser = (User) httpSession.getAttribute(existingUser.get().getEmail());
            if (loggedInUser != null) {
                return "Welcome Back";
            } else {
                httpSession.setAttribute(loginDto.getEmail(), existingUser.get());
                return "User login Successfully";
            }
        }
        throw new UserNotFoundException("User not Found");
    }

    @Override
    public String addLocation(DeliveryDto deliveryDto, HttpSession httpSession) {
        Delivery delivery = new Delivery();
        Optional<User> existingUser = userRepository.findUserByEmail(deliveryDto.getEmail());
        if (existingUser.isPresent()) {
            User loggedInUser = (User) httpSession.getAttribute(deliveryDto.getEmail());
            if (loggedInUser != null) {
                delivery.setAddress(deliveryDto.getAddress());
                delivery.setUser(loggedInUser);
                deliveryRepository.save(delivery);
                return "Location added Successfully";
            } else return "User not currently logged in";
        }
        throw new UserNotFoundException("User not Found");
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
        } else return "User not currently logged in";

    }


}
