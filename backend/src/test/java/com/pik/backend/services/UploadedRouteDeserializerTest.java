package com.pik.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.backend.custom_daos.Coordinates;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UploadedRouteDeserializerTest {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldDeserializeCorrectlyWhenInputIsValid() throws IOException {
        String uploadedRouteJson = FileUtils.readFileToString(new File("src/main/resources/here_maps_json_example.json"), "UTF-8");
        UploadRoute result = objectMapper.readValue(uploadedRouteJson, UploadRoute.class)
                .setBorders()
                .setLengthsAndTime();
        assertEquals(2736, (long) result.getUserId());
        List<RoutePoint> points = result.getPoints();
        assertEquals(403, points.size());
        RoutePoint somePoint = points.get(20);
        Coordinates coordinates = somePoint.getCoordinates();
        assertEquals(19.361493, coordinates.getLongitude(), 0);
        assertEquals(51.790792, coordinates.getLatitude(), 0);
        assertEquals(231.0, coordinates.getElevation(), 0);
        assertEquals(9.46, somePoint.getLength(), 0.01);
        assertEquals(3.0, somePoint.getTravelTimeSeconds(), 0.01);
    }
}