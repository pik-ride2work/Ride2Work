package com.pik.backend.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pik.backend.custom_daos.Coordinates;
import com.pik.backend.util.Distance;

import java.sql.Timestamp;
import java.util.List;

@JsonDeserialize(using = UploadRouteDeserializer.class)
public class UploadRoute {
    private final Integer userId;
    private final List<RoutePoint> points;
    private Coordinates topRightBorder;
    private Coordinates bottomLeftBorder;

    public UploadRoute(List<RoutePoint> points, Integer userId) {
        this.points = points;
        this.userId = userId;
    }

    public UploadRoute calcBorders() {
        Double minLat = null, minLon = null, maxLat = null, maxLon = null;
        for (RoutePoint point : points) {
            Coordinates coordinates = point.getCoordinates();
            minLat = (minLat == null) ? coordinates.getLatitude() : Math.min(minLat, coordinates.getLatitude());
            minLon = (minLon == null) ? coordinates.getLongitude() : Math.min(minLat, coordinates.getLongitude());
            maxLat = (maxLat == null) ? coordinates.getLatitude() : Math.max(minLat, coordinates.getLatitude());
            maxLon = (maxLon == null) ? coordinates.getLongitude() : Math.max(minLat, coordinates.getLongitude());
        }
        this.bottomLeftBorder = new Coordinates(minLat, minLon, 0d);
        this.topRightBorder = new Coordinates(maxLat, maxLon, 0d);
        return this;
    }

    public UploadRoute calcLengthsAndTime() {
        for (int i = 1; i < points.size(); i++) {
            RoutePoint prev = points.get(i - 1);
            RoutePoint curr = points.get(i);
            double dist = Distance.between(prev, curr);
            double travelTimeSeconds = timeDiffSeconds(prev.getTimestamp(), curr.getTimestamp());
            curr.setLength(dist);
            curr.setTravelTimeSeconds(travelTimeSeconds);
        }
        return this;
    }

    protected static double timeDiffSeconds(Timestamp prev, Timestamp curr) {
        return (double) (curr.getTime() - prev.getTime()) / 1_000;
    }

    public List<RoutePoint> getPoints() {
        return points;
    }

    public Integer getUserId() {
        return userId;
    }

    public Coordinates getTopRightBorder() {
        return topRightBorder;
    }

    public Coordinates getBottomLeftBorder() {
        return bottomLeftBorder;
    }

}
