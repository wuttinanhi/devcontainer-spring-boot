package com.example.demo.service;

import com.example.demo.dto.CatCreateDto;
import com.example.demo.dto.CatUpdateDto;
import com.example.demo.entity.Cat;
import com.example.demo.repository.CatPagingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatService {
    @Autowired
    CatPagingRepository catPagingRepository;

    public Cat createCat(CatCreateDto cat) {
        Cat newCat = new Cat();
        newCat.setName(cat.getName());
        newCat.setAge(cat.getAge());
        return catPagingRepository.save(newCat);
    }

    public Cat updateCat(long id, CatUpdateDto cat) {
        Cat updateCat = catPagingRepository.findById(id).orElseThrow();
        updateCat.setName(cat.getName());
        updateCat.setAge(cat.getAge());
        return catPagingRepository.save(updateCat);
    }
}
