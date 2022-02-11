package com.example.demo.guard;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;

public class JwtGuard implements IGuardHandler {
    @Autowired
    AuthService authService;

    @Override
    public boolean process(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authService.splitAuthorizationBearer(authorizationHeader);

        if (authService.validateJwtToken(accessToken) == true) {
            return true;
        }

        return false;
    }

}
