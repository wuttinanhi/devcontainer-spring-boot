package com.example.demo.controller;

import com.example.demo.annotation.AuthUser;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidLoginException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.response.CreateResponse;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDto userCreateDto) throws UserAlreadyExistsException {
        User user = authService.register(userCreateDto.getUsername(), userCreateDto.getPassword());
        return new CreateResponse<Integer>(user.getId());
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody UserLoginDto userLoginDto) throws UserNotFoundException, InvalidLoginException {
        User user = authService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
        String accessToken = jwtService.generateAccessToken(user.getId());
        return accessToken;
    }

    @GetMapping("/auth/user")
    public User user(@AuthUser User user) {
        return user;
    }
}
