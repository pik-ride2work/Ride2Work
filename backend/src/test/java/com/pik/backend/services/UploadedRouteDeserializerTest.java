package com.pik.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UploadedRouteDeserializerTest {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnDouble(){
        Timestamp start = Timestamp.from(Instant.ofEpochMilli(0));
        Timestamp end = Timestamp.from(Instant.ofEpochMilli(2500));
        double result = UploadedRoute.timeDiffSeconds(start, end);
        assertEquals(2.5d, result, 0.001);
    }

    @Test
    public void shouldDeserializeCorrectlyWhenInputIsValid() throws IOException {
        String uploadedRouteJson = FileUtils.readFileToString(new File("src/main/resources/here_maps_json_example.json"), "UTF-8");
        UploadedRoute result = objectMapper.readValue(uploadedRouteJson, UploadedRoute.class);
        assertEquals(2736, (long)result.getUserId());
        List<RoutePoint> points = result.getPoints();
        assertEquals(403, points.size());
        RoutePoint somePoint = points.get(20);
        assertEquals(19.361493, somePoint.getLongitude(), 0);
        assertEquals(51.790792, somePoint.getLatitude(), 0);
        assertEquals(231.0, somePoint.getElevation(), 0);
        assertEquals(9.46, somePoint.getLength(), 0.01);
        assertEquals(3.0, somePoint.getTravelTimeSeconds(), 0.01);
    }
}