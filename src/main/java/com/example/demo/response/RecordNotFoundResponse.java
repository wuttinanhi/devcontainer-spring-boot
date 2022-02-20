package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RecordNotFoundResponse extends ResponseEntity<Object> {
    protected static class Response {
        public HttpStatus status = HttpStatus.NOT_FOUND;
        public String message = "Record not found.";
    }

    public RecordNotFoundResponse() {
        super(new Response(), HttpStatus.NOT_FOUND);
    }
}
