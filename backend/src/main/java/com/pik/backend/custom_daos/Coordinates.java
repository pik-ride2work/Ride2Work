package com.pik.backend.custom_daos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CoordinatesDeserializer.class)
public class Coordinates {
    private double latitude;
    private double longitude;
    private double elevation;

    public Coordinates(double lat, double lon, double ele) {
        this.latitude = lat;
        this.longitude = lon;
        this.elevation = ele;
    }

    Coordinates(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getElevation() {
        return elevation;
    }
}
