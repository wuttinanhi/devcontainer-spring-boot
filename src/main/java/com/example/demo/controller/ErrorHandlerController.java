package com.example.demo.controller;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import com.example.demo.exception.InvalidLogin;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFound;
import com.example.demo.response.InternalServerErrorResponse;
import com.example.demo.response.InvalidInputResponse;
import com.example.demo.response.InvalidLoginResponse;
import com.example.demo.response.RecordAlreadyExistsResponse;
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

    @ExceptionHandler({
            EntityNotFoundException.class, EmptyResultDataAccessException.class,
            NoSuchElementException.class, UserNotFound.class
    })
    public ResponseEntity<Object> notFoundExceptionHandler() {
        return new RecordNotFoundResponse();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> AccessDeniedExceptionHandler() {
        return new UnauthorizedResponse();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> UserAlreadyExistsExceptionHandler() {
        return new RecordAlreadyExistsResponse();
    }

    @ExceptionHandler(InvalidLogin.class)
    public ResponseEntity<?> InvalidLoginHandler() {
        return new InvalidLoginResponse();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> unhandleErrorHandler(RuntimeException exception) {
        exception.printStackTrace();
        return new InternalServerErrorResponse();
    }
}
