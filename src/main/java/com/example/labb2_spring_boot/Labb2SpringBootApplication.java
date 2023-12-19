package com.example.labb2_spring_boot;

import org.geolatte.geom.G2D;
import org.geolatte.geom.crs.CoordinateReferenceSystem;
import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@SpringBootApplication
public class Labb2SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Labb2SpringBootApplication.class, args);
    }

    @Bean
    GeolatteGeomModule geolatteGeomModule() {
        CoordinateReferenceSystem<G2D> crs = WGS84;
        return new GeolatteGeomModule(crs);
    }

}
