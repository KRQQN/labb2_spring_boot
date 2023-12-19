package com.example.labb2_spring_boot.requestBodies;


public record AddLocationReqBody(String name, boolean exposed, String description, String category, double longitude,
                                 double latitude) {

}
