package com.klasha.services.serviceImpl;

import com.klasha.dtos.SignupDto;
import com.klasha.models.User;
import com.klasha.repositories.UserRepository;
import com.klasha.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public String register(SignupDto signupDto) {
        User user = new User();
        Optional<User> existingUser = userRepository.findUserByEmail(signupDto.getEmail())
        return "User Registered Successfully";
    }
}
