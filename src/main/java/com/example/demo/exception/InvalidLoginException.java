package com.example.demo.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("InvalidLoginException");
    }
}
