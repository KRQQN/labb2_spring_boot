package com.example.labb2_spring_boot.requestBodies;

import com.example.labb2_spring_boot.annotations.CategoryConstraint;
import jakarta.validation.constraints.NotEmpty;

@CategoryConstraint
public record AddCategoryReqBody(@NotEmpty String name, String description) {
}
