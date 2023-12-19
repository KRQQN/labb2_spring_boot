package com.example.labb2_spring_boot.controllers;

import com.example.labb2_spring_boot.dataTransferObjects.CategoryDto;
import com.example.labb2_spring_boot.dataTransferObjects.CategoryIdNameDto;
import com.example.labb2_spring_boot.exeptions.EntityNotFoundException;
import com.example.labb2_spring_boot.requestBodies.AddCategoryReqBody;
import com.example.labb2_spring_boot.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("categories")
    public ResponseEntity<List<CategoryIdNameDto>> getAll() {
        if(categoryService.getAll().isEmpty()) throw new EntityNotFoundException("Category");
        else return ResponseEntity.ok().body(categoryService.getAll());

    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getOne(@PathVariable Long id) {
        return categoryService.getById(id).map(c -> ResponseEntity.ok().body(c))
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addOne(@Valid @RequestBody AddCategoryReqBody reqBody) {
        CategoryDto createdCategory = categoryService.addOne(reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.id())
                .toUri();

        return ResponseEntity.created(uri).body(createdCategory);
    }

}