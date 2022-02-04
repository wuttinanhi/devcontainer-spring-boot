package com.example.demo.controller;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import com.example.demo.response.InvalidInputResponse;
import com.example.demo.response.RecordNotFound;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class ErrorHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return new ResponseEntity<Object>(new InvalidInputResponse(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> notFoundExceptionHandler() {
        return new ResponseEntity<Object>(new RecordNotFound(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> EmptyResultDataAccessExceptionHandler() {
        return new ResponseEntity<Object>(new RecordNotFound(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> EntityNotFoundExceptionHandler() {
        return new ResponseEntity<Object>(new RecordNotFound(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public String unhandleErrorHandler(RuntimeException exception) {
        exception.printStackTrace();
        return "Unhandled exception!";
    }
}
