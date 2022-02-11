package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UnauthorizedResponse extends ResponseEntity<Object> {
    public UnauthorizedResponse() {
        super("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
