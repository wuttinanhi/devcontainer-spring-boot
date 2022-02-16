package com.example.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class UserCreateDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 10)
    public String username;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    public String password;
}
