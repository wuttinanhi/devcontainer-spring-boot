package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InternalServerErrorResponse extends ResponseEntity<Object> {
    protected static class Response {
        public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        public String message = "Internal server error.";
    }

    public InternalServerErrorResponse() {
        super(new Response(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
