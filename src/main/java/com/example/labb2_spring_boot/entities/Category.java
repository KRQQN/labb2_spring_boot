package com.example.labb2_spring_boot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Category {

    @Id
    @GeneratedValue
    @Column(name="category_id")
    private Integer categoryID;
    @Size(max=255)
    @NotEmpty
    @Column(name="category_name", unique=true)
    private String categoryName;
    private String category_description;

}
