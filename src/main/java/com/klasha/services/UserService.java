package com.klasha.services;

import com.klasha.dtos.SignupDto;

public interface UserService {
    String register(SignupDto signupDto);
}
