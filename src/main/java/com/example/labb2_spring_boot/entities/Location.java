package com.example.labb2_spring_boot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
@Entity
public class Location {
    @NotEmpty
    @Column(name="location_name")
    private String locationName;
    private Category category;
    @Column(name="created_by")
    private Integer createdBy;
    @Column(name="is_public")
    private Boolean isPublic;
    private LocalDateTime modified;
    private LocalDateTime created;
    @Column(name="location_description")
    private String locationDescription;
    // longitude latitude
}
