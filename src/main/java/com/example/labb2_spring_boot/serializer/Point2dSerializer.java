package com.example.labb2_spring_boot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import java.io.IOException;

public class Point2dSerializer extends JsonSerializer<Point<G2D>> {
    @Override
    public void serialize(Point<G2D> point, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("Latitude: ", point.getPosition().getLat());
        jsonGenerator.writeNumberField("Longitude: ", point.getPosition().getLon());
        jsonGenerator.writeEndObject();
    }
}
