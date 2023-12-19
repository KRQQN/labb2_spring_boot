package com.example.labb2_spring_boot.dataTransferObjects;

import com.example.labb2_spring_boot.entities.Location;

public record LocationDto(Long id, String name, String createdBy, String description, String category) {

    public LocationDto(Location location) {
        this(location.getID(), location.getName(), location.getCreatedBy(), location.getDescription(), location.getCategory().getName());
    }

    public static LocationDto of(Location location) {
        return new LocationDto(location.getID(), location.getName(), location.getCreatedBy(), location.getDescription(), location.getCategory().getName());
    }
}
