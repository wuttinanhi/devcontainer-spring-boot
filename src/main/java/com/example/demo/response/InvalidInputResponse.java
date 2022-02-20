package com.example.demo.response;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class InvalidInputResponse extends ResponseEntity<Object> {
    protected static class Response {
        public String status = "Bad Request";
        public HashMap<String, String> errors = new HashMap<>();

        public Response(MethodArgumentNotValidException exception) {
            for (FieldError fieldError : exception.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
    }

    public InvalidInputResponse(MethodArgumentNotValidException exception) {
        super(new Response(exception), HttpStatus.BAD_REQUEST);
    }
}
