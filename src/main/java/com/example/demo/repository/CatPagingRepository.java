package com.example.demo.repository;

import com.example.demo.entity.Cat;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatPagingRepository extends PagingAndSortingRepository<Cat, Long> {

}
