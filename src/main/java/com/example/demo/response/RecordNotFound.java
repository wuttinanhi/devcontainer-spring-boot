package com.example.demo.response;

import org.springframework.http.HttpStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RecordNotFound {
    public HttpStatus status = HttpStatus.NOT_FOUND;
    public String message = "Record not found!";
}
