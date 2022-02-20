package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UnauthorizedResponse extends ResponseEntity<Object> {
    protected static class Response {
        public HttpStatus status = HttpStatus.UNAUTHORIZED;
        public String message = "Unauthorized.";
    }

    public UnauthorizedResponse() {
        super(new Response(), HttpStatus.UNAUTHORIZED);
    }
}
