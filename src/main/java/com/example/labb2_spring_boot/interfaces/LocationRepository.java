package com.example.labb2_spring_boot.interfaces;

import com.example.labb2_spring_boot.entities.Location;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends ListCrudRepository<Location, Integer> {

    @Query("SELECT l FROM Location l WHERE l.exposed = true")
    List<Location> getAllPublicLocations();

    @Query("SELECT l FROM Location l WHERE l.createdBy = :currentUser")
    List<Location> getByCreatedByCurrentUser(@Param("currentUser") String currentUser);

    @Query("SELECT l FROM Location l WHERE l.ID = :id AND l.exposed = true OR l.createdBy = :currentUser AND l.ID =:id")
    Optional<Location> getByIDOrCreatedBy(@Param("id") Long id, @Param("currentUser") String currentUser);

    Optional<Location> getByIDAndCreatedBy(@Param("id") Long id, @Param("currentUser") String currentUser);

    Boolean deleteByID(Long id);

    @Query("SELECT l FROM Location l WHERE st_distance_sphere(l.coordinate, :fromLocationG2D) < :meters")
    List<Location> getLocationsWithinMetersFromLocation(@Param("meters") Long meters, @Param("fromLocationG2D") Point<G2D> fromLocationG2D);
}