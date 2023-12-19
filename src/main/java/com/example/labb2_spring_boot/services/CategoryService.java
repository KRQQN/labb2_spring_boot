package com.example.labb2_spring_boot.services;

import com.example.labb2_spring_boot.dataTransferObjects.CategoryDto;
import com.example.labb2_spring_boot.dataTransferObjects.CategoryIdNameDto;
import com.example.labb2_spring_boot.entities.Category;
import com.example.labb2_spring_boot.entities.Location;
import com.example.labb2_spring_boot.interfaces.CategoryRepository;
import com.example.labb2_spring_boot.requestBodies.AddCategoryReqBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryIdNameDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryIdNameDto::new)
                .toList();
    }

    public Optional<CategoryDto> getById(@PositiveOrZero Long id) {
        return categoryRepository.findById(id).map(CategoryDto::new);
    }

    protected Category getByName(String categoryName) {
        return categoryRepository.getByName(categoryName);
    }

    //questionable? breaking single responsibility rule?
    protected List<Location> getPublicLocationsByCategory(String categoryName) {
        return categoryRepository.getPublicLocationsByCategory(categoryName);
    }

    public CategoryDto addOne(@Valid @RequestBody AddCategoryReqBody reqBody) {
        Category category = new Category();

        category.setName(reqBody.name());
        category.setDescription(reqBody.description());
        return CategoryDto.of(categoryRepository.save(category));
    }

    public boolean categoryExists(@NotEmpty String categoryName) {
        return categoryRepository.existsByName(categoryName);
    }
}
