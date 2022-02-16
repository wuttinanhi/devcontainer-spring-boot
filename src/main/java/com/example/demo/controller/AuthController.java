package com.example.demo.controller;

import com.example.demo.annotation.UseGuard;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidLogin;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFound;
import com.example.demo.guard.JwtGuard;
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
    public ResponseEntity<?> register(@RequestBody UserCreateDto userDto) throws UserAlreadyExistsException {
        User user = authService.register(userDto.getUsername(), userDto.getPassword());
        return new CreateResponse<Integer>(user.getId());
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody UserLoginDto userLoginDto) throws UserNotFound, InvalidLogin {
        User user = authService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
        String accessToken = jwtService.generateAccessToken(user.getId());
        return accessToken;
    }

    @UseGuard({ JwtGuard.class })
    @GetMapping("/auth/user")
    public String user() {
        return "user route";
    }
}
