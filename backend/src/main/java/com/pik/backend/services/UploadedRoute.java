package com.pik.backend.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UploadedRoute {
    private final List<RoutePoint> points;
    private final Long userId;
    private final Integer lengthInMeters;
    private final Integer timeInSeconds;
    private Double maxLatitude;
    private Double minLatitude;
    private Double maxLongitude;
    private Double minLongitude;

    public UploadedRoute(List<RoutePoint> points, Long userId, Integer lengthInMeters, Integer timeInSeconds) {
        this.points = points;
        this.userId = userId;
        this.lengthInMeters = lengthInMeters;
        this.timeInSeconds = timeInSeconds;
        setBorders(points);
    }

    private void setBorders(List<RoutePoint> points) {
        Optional<Double> maxLat = points.stream()
                .map(RoutePoint::getLatitude)
                .max(Comparator.comparing(Double::valueOf));
        this.maxLatitude = maxLat.orElse(null);
        Optional<Double> minLat = points.stream()
                .map(RoutePoint::getLatitude)
                .min(Comparator.comparing(Double::valueOf));
        this.minLatitude = minLat.orElse(null);
        Optional<Double> maxLon = points.stream()
                .map(RoutePoint::getLongitude)
                .max(Comparator.comparing(Double::valueOf));
        this.maxLongitude = maxLon.orElse(null);
        Optional<Double> minLon = points.stream()
                .map(RoutePoint::getLongitude)
                .min(Comparator.comparing(Double::valueOf));
        this.minLongitude = minLon.orElse(null);
    }

    public List<RoutePoint> getPoints() {
        return points;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getLengthInMeters() {
        return lengthInMeters;
    }

    public Integer getTimeInSeconds() {
        return timeInSeconds;
    }

    public Double getMaxLatitude() {
        return maxLatitude;
    }

    public Double getMinLatitude() {
        return minLatitude;
    }

    public Double getMaxLongitude() {
        return maxLongitude;
    }

    public Double getMinLongitude() {
        return minLongitude;
    }
}
