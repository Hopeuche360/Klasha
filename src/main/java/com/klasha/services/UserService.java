package com.klasha.services;

import com.klasha.dtos.LoginDto;
import com.klasha.dtos.SignupDto;

import javax.servlet.http.HttpSession;

public interface UserService {
    String register(SignupDto signupDto);
    String login(LoginDto loginDto, HttpSession httpSession);
}
