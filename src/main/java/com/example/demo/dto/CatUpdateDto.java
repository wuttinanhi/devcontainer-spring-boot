package com.example.demo.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CatUpdateDto {
    @Size(max = 10)
    public String name;

    @Positive()
    @Max(100)
    public int age;
}
