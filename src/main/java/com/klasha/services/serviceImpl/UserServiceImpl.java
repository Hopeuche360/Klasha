package com.klasha.services.serviceImpl;

import com.klasha.dtos.SignupDto;
import com.klasha.exceptions.CustomAppException;
import com.klasha.models.User;
import com.klasha.repositories.UserRepository;
import com.klasha.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
