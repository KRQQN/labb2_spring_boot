package com.example.labb2_spring_boot.interfaces;

import com.example.labb2_spring_boot.entities.Category;
import com.example.labb2_spring_boot.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {
    Boolean existsByName(String categoryName);

    Category getByName(String categoryName);

    @Query("SELECT l FROM Location l WHERE l.category.name = :categoryName AND l.exposed = true")
    List<Location> getPublicLocationsByCategory(@Param("categoryName") String categoryName);

}




