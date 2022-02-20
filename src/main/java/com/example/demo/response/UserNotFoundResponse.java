package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserNotFoundResponse extends ResponseEntity<Object> {
    protected static class Response {
        public HttpStatus status = HttpStatus.NOT_FOUND;
        public String message = "User not found.";
    }

    public UserNotFoundResponse() {
        super(new Response(), HttpStatus.NOT_FOUND);
    }
}
