package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InvalidLoginResponse extends ResponseEntity<Object> {
    protected static class Response {
        public HttpStatus status = HttpStatus.UNAUTHORIZED;
        public String message = "Invalid login.";
    }

    public InvalidLoginResponse() {
        super(new Response(), HttpStatus.UNAUTHORIZED);
    }
}
