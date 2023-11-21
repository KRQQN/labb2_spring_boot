package com.example.labb2_spring_boot.services;

import com.example.labb2_spring_boot.entities.Category;
import com.example.labb2_spring_boot.interfaces.CategoryRepository;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    List<String> dummy = List.of("McDonalds", "Favoriter", "Vandringsleder");

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(@PositiveOrZero int id) {
        //if(id > dummy.size()) return Optional.empty();
        return categoryRepository.findById(id);
    }

    /*public Optional<Category> getCategory(@PositiveOrZero int id) {
        if(id > dummy.size()) return Optional.empty();
        return Optional.of(dummy.get(id));
    }*/
}
