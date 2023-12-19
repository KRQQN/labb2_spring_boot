package com.example.labb2_spring_boot.dataTransferObjects;

import com.example.labb2_spring_boot.entities.Location;

public record LocationIdNameDto(Long id, String name, boolean exposed) {
    public LocationIdNameDto(Location location) {
        this(location.getID(), location.getName(), location.getExposed());
    }
}
