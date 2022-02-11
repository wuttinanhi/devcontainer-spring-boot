package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RecordNotFoundResponse extends ResponseEntity<Object> {
    public RecordNotFoundResponse() {
        super("Record not found", HttpStatus.NOT_FOUND);
    }
}
