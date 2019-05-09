package com.pik.backend.services;

import com.google.common.base.Strings;

import java.sql.Timestamp;
import java.time.Instant;

public class RoutePoint {
    private final Long id;
    private final Timestamp timestamp;
    private final Double longitude;
    private final Double latitude;
    private static final Integer NUMBER_OF_PARTS = 4;
    private static final Double MAX_LON = 180.0;
    private static final Double MIN_LON = -180.0;
    private static final Double MAX_LAT = 90.0;
    private static final Double MIN_LAT = -90.0;

    private RoutePoint(Long id, Timestamp timestamp, Double longitude, Double latitude) {
        this.id = id;
        this.timestamp = timestamp;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Route point line protocol defines expected contents of a single point. It consist of 4 elements separated by a space character:
     * - unique route id
     * - geographical longitude of the point
     * - geographical latitude of the point
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
        Double longitude = Double.valueOf(parts[2]);
        if (longitude < MIN_LON || longitude > MAX_LON) {
            throw new IllegalArgumentException(String.format("Longitude value should be within <%s, %s> range", MIN_LON, MAX_LON));
        }
        Double latitude = Double.valueOf(parts[3]);
        if (latitude < MIN_LAT || latitude > MAX_LAT) {
            throw new IllegalArgumentException(String.format("Latitude value should be within <%s, %s> range", MIN_LAT, MAX_LAT));
        }
        try {
            return new RoutePoint(
                    Long.valueOf(parts[0]),
                    Timestamp.from(Instant.ofEpochMilli(Long.valueOf(parts[1]))),
                    longitude,
                    latitude
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Point format is invalid.");
        }

    }

    public Long getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
