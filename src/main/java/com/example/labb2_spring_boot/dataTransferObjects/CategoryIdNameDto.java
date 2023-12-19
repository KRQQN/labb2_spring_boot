package com.example.labb2_spring_boot.dataTransferObjects;

import com.example.labb2_spring_boot.entities.Category;

public record CategoryIdNameDto(Long id, String name) {
    public CategoryIdNameDto(Category category) {
        this(category.getID(), category.getName());
    }
}

