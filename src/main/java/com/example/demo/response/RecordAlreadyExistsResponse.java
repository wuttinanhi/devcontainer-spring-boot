package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RecordAlreadyExistsResponse extends ResponseEntity<Object> {
    protected static class Response {
        public HttpStatus status = HttpStatus.CONFLICT;
        public String message = "Record already exists.";
    }

    public RecordAlreadyExistsResponse() {
        super(new Response(), HttpStatus.CONFLICT);
    }
}
