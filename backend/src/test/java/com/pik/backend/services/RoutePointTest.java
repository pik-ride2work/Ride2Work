package com.pik.backend.services;

import com.pik.backend.custom_daos.Coordinates;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

public class RoutePointTest {

    private static String SOME_VALID_POINT = "7687213 1557415405000 23.78327283 24.123127389 243.2";
    private static String SOME_INVALID_POINT = "6782537 24.904 1.5643 1.11";

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPointIsNull(){
        RoutePoint.of(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenLineProtocolFormatIsInvalid(){
        RoutePoint.of(SOME_INVALID_POINT);
    }

    @Test
    public void shouldReturnPointWhenInputIsCorrect(){
        RoutePoint result = RoutePoint.of(SOME_VALID_POINT);
        assertEquals(7687213, (long)result.getRouteId());
        Coordinates coordinates = result.getCoordinates();
        assertEquals(23.78327283, coordinates.getLongitude(), 0);
        assertEquals(24.123127389, coordinates.getLatitude(), 0);
        assertEquals(243.2, coordinates.getElevation(), 0);
        Timestamp expectedTimestamp = new Timestamp(1557415405000L);
        assertEquals(expectedTimestamp, result.getTimestamp());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenLatitudeIsOutOfRange(){
        String point = "7687213 1557415405000 23.78327283 224.123127389";
        RoutePoint.of(point);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenLongitudeIsOutOfRange(){
        String point = "7687213 1557415405000 -123.78327283 124.123127389";
        RoutePoint.of(point);
    }
}