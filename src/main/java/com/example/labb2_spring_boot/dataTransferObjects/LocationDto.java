package com.example.labb2_spring_boot.dataTransferObjects;

import com.example.labb2_spring_boot.entities.Location;
import com.example.labb2_spring_boot.serializer.Point2dSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

public record LocationDto(Long id,
                          String name,
                          String createdBy,
                          String description,
                          String category,
                          @JsonSerialize(using = Point2dSerializer.class)
                          Point<G2D> coordinate) {

    public LocationDto(Location location) {
        this(location.getID(), location.getName(), location.getCreatedBy(), location.getDescription(), location.getCategory().getName() , location.getCoordinate());
    }

    public static LocationDto of(Location location) {
        return new LocationDto(location.getID(), location.getName(), location.getCreatedBy(), location.getDescription(), location.getCategory().getName(), location.getCoordinate());
    }
}
