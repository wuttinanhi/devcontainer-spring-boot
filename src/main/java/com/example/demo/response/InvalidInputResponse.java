package com.example.demo.response;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class InvalidInputResponse {
    public HttpStatus status = HttpStatus.BAD_GATEWAY;
    public HashMap<String, String> errors = new HashMap<>();

    public InvalidInputResponse(MethodArgumentNotValidException exception) {
        for (FieldError fieldError : exception.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
