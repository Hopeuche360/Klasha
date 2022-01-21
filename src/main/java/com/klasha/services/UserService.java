package com.klasha.services;

import com.klasha.dtos.LoginDto;
import com.klasha.dtos.SignupDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface UserService {
    String register(SignupDto signupDto);
    String login(LoginDto loginDto, HttpSession httpSession);
}
