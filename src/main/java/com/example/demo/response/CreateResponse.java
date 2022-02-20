package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreateResponse<T> extends ResponseEntity<Object> {
    protected static class Response<T> {
        public String status = "Created";
        public T id;

        public Response(T id) {
            this.id = id;
        }
    }

    public CreateResponse(T id) {
        super(new Response<T>(id), HttpStatus.CREATED);
    }
}
