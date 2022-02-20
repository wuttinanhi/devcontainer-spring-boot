package com.example.demo.controller;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.demo.exception.InvalidLoginException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.response.InternalServerErrorResponse;
import com.example.demo.response.InvalidInputResponse;
import com.example.demo.response.InvalidLoginResponse;
import com.example.demo.response.RecordAlreadyExistsResponse;
import com.example.demo.response.RecordNotFoundResponse;
import com.example.demo.response.UnauthorizedResponse;
import com.example.demo.response.UserNotFoundResponse;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler({ AccessDeniedException.class, JWTDecodeException.class })
    public ResponseEntity<Object> AccessDeniedExceptionHandler() {
        return new UnauthorizedResponse();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> UserAlreadyExistsExceptionHandler() {
        return new RecordAlreadyExistsResponse();
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<?> InvalidLoginHandler() {
        return new InvalidLoginResponse();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> UserNotFoundExceptionHandler() {
        return new UserNotFoundResponse();
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            EmptyResultDataAccessException.class,
            NoSuchElementException.class,
    })
    public ResponseEntity<Object> notFoundExceptionHandler() {
        return new RecordNotFoundResponse();
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, IllegalArgumentException.class })
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return new InvalidInputResponse(exception);
    }

    @ExceptionHandler(RuntimeException.class)
    public Object unhandleErrorHandler(RuntimeException exception) {
        exception.printStackTrace();
        return new InternalServerErrorResponse();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception exception) {
        exception.printStackTrace();
        return new InternalServerErrorResponse();
    }
}
