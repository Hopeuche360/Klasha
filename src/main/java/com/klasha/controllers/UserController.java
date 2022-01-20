package com.klasha.controllers;

import com.klasha.dtos.LoginDto;
import com.klasha.dtos.SignupDto;
import com.klasha.responses.HttpResponse;
import com.klasha.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    public final UserService userService;

    protected static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new  ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()), httpStatus );
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpResponse> signup(@RequestBody SignupDto signupDto) {
        return response(CREATED, userService.register(signupDto));
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginDto loginDto, HttpSession httpSession) {
        return  response(OK, userService.login(loginDto, httpSession));
    }
}
