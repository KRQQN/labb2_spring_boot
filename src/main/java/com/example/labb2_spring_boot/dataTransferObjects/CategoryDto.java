package com.example.labb2_spring_boot.dataTransferObjects;

import com.example.labb2_spring_boot.entities.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

public record CategoryDto(@PositiveOrZero Long id, @NotEmpty String name, String descriprion) {
    public CategoryDto(Category category) {
        this(category.getID(), category.getName(), category.getDescription());
    }

    public static CategoryDto of(Category category) {
        return new CategoryDto(category.getID(), category.getName(), category.getDescription());
    }
}
