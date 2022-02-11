package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InternalServerErrorResponse extends ResponseEntity<Object> {
    public InternalServerErrorResponse() {
        super("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
