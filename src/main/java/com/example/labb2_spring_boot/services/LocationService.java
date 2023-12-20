package com.example.labb2_spring_boot.services;

import com.example.labb2_spring_boot.dataTransferObjects.LocationDto;
import com.example.labb2_spring_boot.dataTransferObjects.LocationIdNameDto;
import com.example.labb2_spring_boot.entities.Location;
import com.example.labb2_spring_boot.exeptions.EntityNotFoundException;
import com.example.labb2_spring_boot.interfaces.LocationRepository;
import com.example.labb2_spring_boot.requestBodies.AddLocationReqBody;
import com.example.labb2_spring_boot.requestBodies.LocationReqBody;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.PositiveOrZero;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.codec.Wkt;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.labb2_spring_boot.security.Authenticate.getAuthenticatedUserName;
import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CategoryService categoryService;


    public LocationService(LocationRepository locationRepository, CategoryService categoryService) {
        this.locationRepository = locationRepository;
        this.categoryService = categoryService;
    }


    public List<LocationIdNameDto> getAll() {
        String userName = getAuthenticatedUserName();
        List<Location> locations;

        locations = (userName != null)
                ? locationRepository.getByCreatedByCurrentUser(userName)
                : locationRepository.getAllPublicLocations();

        return locations.stream()
                .map(LocationIdNameDto::new)
                .toList();
    }

    public LocationDto patchLocation(@PositiveOrZero Long id, Location update) {
        String userName = getAuthenticatedUserName();
        Optional<Location> locationToPatch = locationRepository.getByIDAndCreatedBy(id, userName);
        Location patchedLocation;

        if (userName != null && locationToPatch.isPresent()) {
            patchedLocation = locationToPatch.get();

            patchedLocation.setID(id);
            patchedLocation.setName(update.getName());
            patchedLocation.setCreatedBy(userName);
            patchedLocation.setExposed(update.getExposed());
            patchedLocation.setDescription(update.getDescription());
            patchedLocation.setCoordinate(update.getCoordinate());

            String currentCategoryName = patchedLocation.getCategory().getName();
            if (!currentCategoryName.equals(update.getName())) {
                categoryService.getByName(currentCategoryName).removeLocation(patchedLocation);
                categoryService.getByName(currentCategoryName).addLocation(patchedLocation);
            }
            return LocationDto.of(locationRepository.save(patchedLocation));

        } else throw new EntityNotFoundException("Location", id);

    }

    public Optional<LocationDto> getById(@PositiveOrZero Long id) {
        String userName = getAuthenticatedUserName();

        return locationRepository.getByIDOrCreatedBy(id, userName).map(LocationDto::new);

    }

    public List<LocationDto> getAllByCategory(String categoryName) {
        return categoryService.getPublicLocationsByCategory(categoryName).stream().map(LocationDto::new).toList();
    }

    @Transactional
    public LocationDto addOne(AddLocationReqBody req) {
        String userName = getAuthenticatedUserName();
        boolean validCoordinates = coordinateValidation(req.longitude(), req.latitude());

        if (userName != null && validCoordinates) {

            Location location = new Location();
            location.setName(req.name());
            location.setCreatedBy(userName);
            location.setExposed(req.exposed());
            location.setDescription(req.description());
            location.setCoordinate(
                    (Point<G2D>) Wkt.fromWkt(STR. "Point(\{ req.longitude() } \{ req.latitude() })" , WGS84)
            );

            categoryService.getByName(req.category()).addLocation(location);
            return LocationDto.of(locationRepository.save(location));
        } else return null; // sorry
    }

    @Transactional
    public boolean removeOne(Long id) {
        String userName = getAuthenticatedUserName();
        Optional<Location> location = locationRepository.getByIDAndCreatedBy(id, userName);

        if (location.isPresent()) {
            String locationName = location.get().getName();
            Location locationToRemove = location.get();

            categoryService.getByName(locationName).removeLocation(locationToRemove);
            return locationRepository.deleteByID(id);
        } else return false;
    }

    private boolean coordinateValidation(double longitude, double latitude) {
        return ((longitude >= -180.0) && (longitude <= 180.0)) && ((latitude >= -90.0) && (latitude <= 90.0));
    }


    public List<LocationDto> getLocationsWithinMeters(Long meters, LocationReqBody fromLocation) {
        var fromLocationG2D = Geometries.mkPoint( new G2D(fromLocation.longitude(), fromLocation.latitude()), WGS84);

        return locationRepository.getLocationsWithinMetersFromLocation(meters, fromLocationG2D)
                .stream().map(LocationDto::new).toList();
    }
}
