package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean validateJwtToken(String token) {
        if (token.equals("VALID-JWT-TOKEN"))
            return true;
        return false;
    }

}
