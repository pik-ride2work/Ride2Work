package com.pik.backend.services;

import com.google.common.base.Strings;

import java.sql.Timestamp;
import java.time.Instant;

public class RoutePoint {
    private final Integer routeId;
    private final Timestamp timestamp;
    private final double longitude;
    private final double latitude;
    private final double elevation;
    private double length;
    private double travelTimeSeconds;
    private static final int NUMBER_OF_PARTS = 5;
    private static final double MAX_LON = 180.0;
    private static final double MIN_LON = -180.0;
    private static final double MAX_LAT = 90.0;
    private static final double MIN_LAT = -90.0;

    public RoutePoint(Integer routeId, Timestamp timestamp, double longitude, double latitude, double elevation) {
        this.routeId = routeId;
        this.timestamp = timestamp;
        this.longitude = longitude;
        this.latitude = latitude;
        this.elevation = elevation;
    }

    public RoutePoint(Timestamp timestamp, double longitude, double latitude, double elevation) {
        this(null, timestamp, longitude, latitude, elevation);
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
                    longitude,
                    latitude,
                    elevation
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Point format is invalid.");
        }

    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
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

    public Double getElevation() {
        return elevation;
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

}
