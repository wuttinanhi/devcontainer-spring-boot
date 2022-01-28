package com.example.demo.controllers;

import com.example.demo.entity.Cat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatController {
    @GetMapping("/cat")
    public String indexCat() {
        return "Cat route!";
    }

    @GetMapping("/cat/get")
    public Cat getCat() {
        Cat cat = new Cat();
        cat.setName("Cat");
        cat.setAge(1);
        return cat;
    }
}
