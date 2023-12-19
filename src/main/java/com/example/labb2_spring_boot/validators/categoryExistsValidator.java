package com.example.labb2_spring_boot.validators;

import com.example.labb2_spring_boot.annotations.CategoryConstraint;
import com.example.labb2_spring_boot.requestBodies.AddCategoryReqBody;
import com.example.labb2_spring_boot.services.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class categoryExistsValidator implements ConstraintValidator<CategoryConstraint, AddCategoryReqBody> {

    CategoryService categoryService;

    @Override
    public void initialize(CategoryConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(AddCategoryReqBody category, ConstraintValidatorContext ctx) {
        return categoryService.categoryExists(category.name());
    }
}
