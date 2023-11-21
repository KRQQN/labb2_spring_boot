package com.example.labb2_spring_boot.interfaces;

import com.example.labb2_spring_boot.entities.Category;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
}
