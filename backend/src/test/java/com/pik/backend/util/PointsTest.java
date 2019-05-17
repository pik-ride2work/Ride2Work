package com.pik.backend.util;

import com.pik.backend.custom_daos.Coordinates;
import com.pik.backend.services.RoutePoint;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class PointsTest {

    private static final Coordinates ANY_COORDS = new Coordinates(60, 60, 60);
    private static final Integer ANY_ID = 1;

    @Test
    public void shouldReturnDouble() {
        Timestamp start = Timestamp.from(Instant.ofEpochMilli(0));
        Timestamp end = Timestamp.from(Instant.ofEpochMilli(2500));
        RoutePoint one = new RoutePoint(ANY_ID, start, ANY_COORDS);
        RoutePoint two = new RoutePoint(ANY_ID, end, ANY_COORDS);
        double result = Points.timeDiff(one, two);
        assertEquals(2.5d, result, 0.001);
    }
}