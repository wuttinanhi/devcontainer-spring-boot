package com.example.demo.controller;

import com.example.demo.annotation.UseGuard;
import com.example.demo.guard.JwtGuard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @UseGuard({ JwtGuard.class })
    @GetMapping("/auth/secure")
    public String secure() {
        return "secure route";
    }

    @UseGuard({ JwtGuard.class })
    @GetMapping("/auth/user")
    }
}
