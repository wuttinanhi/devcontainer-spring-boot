package com.example.demo.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class CatCreateDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 10)
    public String name;

    @NotNull
    @Positive()
    @Max(100)
    public int age;
}
