package com.pik.backend.services;

import com.google.common.base.Strings;
import com.pik.backend.custom_daos.Coordinates;

import java.sql.Timestamp;
import java.time.Instant;

import static com.pik.backend.util.Distance.*;

public class RoutePoint {
    private final Integer routeId;
    private final Coordinates coordinates;
    private final Timestamp timestamp;
    private double length;
    private double travelTimeSeconds;
    private static final int NUMBER_OF_PARTS = 5;

    public RoutePoint(Integer routeId, Timestamp timestamp, Coordinates coordinates) {
        this.routeId = routeId;
        this.timestamp = timestamp;
        this.coordinates = coordinates;
    }

    public RoutePoint(Timestamp timestamp, Coordinates coordinates) {
        this(null, timestamp, coordinates);
    }

    /**
     * Route point line protocol defines expected contents of a single point. It consist of 4 elements separated by a space character:
     * - unique route id
     * - geographical longitude of the point
     * - geographical latitude of the point
     * - geographical elevation of the point
     * - timestamp representing the time at which the point was measured
     */
    public static RoutePoint of(String point) {
        if (Strings.isNullOrEmpty(point)) {
            throw new IllegalArgumentException("Point is empty.");
        }
        String[] parts = point.split("\\s+");
        if (parts.length != NUMBER_OF_PARTS) {
            throw new IllegalArgumentException("Point format is invalid.");
        }
        double longitude = Double.parseDouble(parts[2]);
        if (longitude < MIN_LON || longitude > MAX_LON) {
            throw new IllegalArgumentException(String.format("Longitude value should be within <%s, %s> range", MIN_LON, MAX_LON));
        }
        double latitude = Double.parseDouble(parts[3]);
        if (latitude < MIN_LAT || latitude > MAX_LAT) {
            throw new IllegalArgumentException(String.format("Latitude value should be within <%s, %s> range", MIN_LAT, MAX_LAT));
        }
        double elevation = Double.parseDouble(parts[4]);
        try {
            return new RoutePoint(
                    Integer.valueOf(parts[0]),
                    Timestamp.from(Instant.ofEpochMilli(Long.valueOf(parts[1]))),
                    new Coordinates(latitude, longitude, elevation)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Point format is invalid.");
        }

    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


    public double getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public double getTravelTimeSeconds() {
        return travelTimeSeconds;
    }

    public void setTravelTimeSeconds(double travelTimeSeconds) {
        this.travelTimeSeconds = travelTimeSeconds;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setTravelTimeSeconds(Integer travelTimeSeconds) {
        this.travelTimeSeconds = travelTimeSeconds;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setTravelTimeSeconds(int travelTimeSeconds) {
        this.travelTimeSeconds = travelTimeSeconds;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

}
