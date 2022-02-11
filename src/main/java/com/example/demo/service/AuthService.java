package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public String splitAuthorizationBearer(String header) {
        return header.split(" ")[1];
    }

    public boolean validateJwtToken(String token) {
        if (token.equals("VALID-JWT-TOKEN"))
            return true;
        return false;
    }

}
