package com.example.labb2_spring_boot.controllers;

import com.example.labb2_spring_boot.dataTransferObjects.LocationDto;
import com.example.labb2_spring_boot.dataTransferObjects.LocationIdNameDto;
import com.example.labb2_spring_boot.entities.Location;
import com.example.labb2_spring_boot.exeptions.EntityNotFoundException;
import com.example.labb2_spring_boot.requestBodies.AddLocationReqBody;
import com.example.labb2_spring_boot.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<LocationIdNameDto>> getAllLocations() {
        List<LocationIdNameDto> allLocations = locationService.getAll();

        if (allLocations.isEmpty()) throw new EntityNotFoundException("Location");
        else return ResponseEntity.ok().body(allLocations);
    }

    @PostMapping
    public ResponseEntity<LocationDto> addLocation(@RequestBody AddLocationReqBody reqBody) {
        LocationDto createdLocation = locationService.addOne(reqBody);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLocation.id())
                .toUri();

        return ResponseEntity.created(uri).body(createdLocation);
    }

    @GetMapping("{id}/remove")
    public ResponseEntity<Void> removeLocation(@PathVariable Long id) {
        if (locationService.removeOne(id)) return ResponseEntity.accepted().build();
        else throw new EntityNotFoundException("Location",id);
    }

    @GetMapping("{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        return locationService.getById(id).map(l -> ResponseEntity.ok().body(l))
                .orElseThrow(() -> new EntityNotFoundException("Location", id));
    }

    @PostMapping("{id}")
    public ResponseEntity<LocationDto> patchLocation(@PathVariable Long id, Location update) {
        LocationDto updatedLocation = locationService.patchLocation(id, update);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedLocation.id())
                .toUri();

        return ResponseEntity.created(uri).body(updatedLocation);
    }


    @GetMapping("category/{categoryName}")
    public ResponseEntity<List<LocationDto>> getLocationsByCategory(@PathVariable String categoryName) {
        List<LocationDto> categoryLocations = locationService.getAllByCategory(categoryName);

        if (categoryLocations.isEmpty()) throw new EntityNotFoundException("Location");
        return ResponseEntity.ok().body(categoryLocations);
    }


}