package com.klasha.services.serviceImpl;

import com.klasha.dtos.LoginDto;
import com.klasha.dtos.SignupDto;
import com.klasha.exceptions.CustomAppException;
import com.klasha.exceptions.UserNotFoundException;
import com.klasha.models.User;
import com.klasha.repositories.UserRepository;
import com.klasha.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


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
                httpSession.setAttribute(existingUser.get().getEmail(), existingUser);
                return "User login Successfully";
            }
        }
        throw new UserNotFoundException("User not Found");
    }
}
