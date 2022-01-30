package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.dto.CatCreateDto;
import com.example.demo.dto.CatUpdateDto;
import com.example.demo.entity.Cat;
import com.example.demo.repository.CatPagingRepository;
import com.example.demo.repository.CatRepository;
import com.example.demo.service.CatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatController {
    @Autowired
    CatRepository catRepository;

    @Autowired
    CatPagingRepository catPagingRepository;

    @Autowired
    CatService catService;

    /**
     * default route
     */
    @GetMapping("/cat")
    public String indexCat() {
        return "Cat route!";
    }

    /**
     * create route
     */
    @PostMapping("/cat/create")
    private Cat createCat(@Valid @RequestBody(required = false) CatCreateDto cat) {
        return catService.createCat(cat);
    }

    /**
     * get route
     */
    @GetMapping("/cat/get/{id}")
    public Cat getCat(@PathVariable Long id) {
        return catRepository.findById(id).get();
    }

    /**
     * update route
     */
    @PatchMapping("/cat/update/{id}")
    public Cat updateCat(@PathVariable Long id, @Valid @RequestBody CatUpdateDto cat) {
        return catService.updateCat(id, cat);
    }

    /**
     * delete route
     */
    @DeleteMapping("/cat/delete/{id}")
    public void deleteCat(@PathVariable Long id) {
        catRepository.deleteById(id);
    }

    /**
     * list all route
     */
    @GetMapping("/cat/all")
    public List<Cat> getCatAll() {
        return catRepository.findAll();
    }

    /**
     * list page route
     */
    @GetMapping("/cat/page")
    public Page<Cat> getCatPage(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return catPagingRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").ascending()));
    }
}
