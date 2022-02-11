package com.example.demo.controller;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import com.example.demo.response.InternalServerErrorResponse;
import com.example.demo.response.InvalidInputResponse;
import com.example.demo.response.RecordNotFoundResponse;
import com.example.demo.response.UnauthorizedResponse;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class ErrorHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return new InvalidInputResponse(exception);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> notFoundExceptionHandler() {
        return new RecordNotFoundResponse();
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> EmptyResultDataAccessExceptionHandler() {
        return new RecordNotFoundResponse();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> EntityNotFoundExceptionHandler() {
        return new RecordNotFoundResponse();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> AccessDeniedExceptionHandler() {
        return new UnauthorizedResponse();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> unhandleErrorHandler(RuntimeException exception) {
        exception.printStackTrace();
        return new InternalServerErrorResponse();
    }
}
