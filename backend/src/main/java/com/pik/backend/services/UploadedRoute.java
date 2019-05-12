package com.pik.backend.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pik.backend.util.Distance;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@JsonDeserialize(using = UploadedRouteDeserializer.class)
public class UploadedRoute {
    private final List<RoutePoint> points;
    private final Integer userId;
    private Double maxLatitude;
    private Double minLatitude;
    private Double maxLongitude;
    private Double minLongitude;

    public UploadedRoute(List<RoutePoint> points, Integer userId) {
        this.points = points;
        this.userId = userId;
        setBorders(points);
        setLengthsAndTime(points);
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

    private void setLengthsAndTime(List<RoutePoint> points) {
        for (int i = 1; i < points.size(); i++) {
            RoutePoint prev = points.get(i - 1);
            RoutePoint curr = points.get(i);
            double dist = Distance.between(prev, curr);
            double travelTimeSeconds = timeDiffSeconds(prev.getTimestamp(), curr.getTimestamp());
            curr.setLength(dist);
            curr.setTravelTimeSeconds(travelTimeSeconds);
        }
    }

    protected static double timeDiffSeconds(Timestamp prev, Timestamp curr) {
        return (double)(curr.getTime() - prev.getTime()) / 1_000;
    }

    public List<RoutePoint> getPoints() {
        return points;
    }

    public Integer getUserId() {
        return userId;
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
