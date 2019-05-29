package com.pik.backend.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pik.backend.custom_daos.Coordinates;
import com.pik.backend.util.Points;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.IntStream;

@JsonDeserialize(using = UploadRouteDeserializer.class)
public class UploadRoute {
    private final Integer userId;
    private final List<RoutePoint> points;
    private double time = 0.0;
    private double length = 0.0;
    private double maxSpeed = 0.0;
    private double averageSpeed = 0.0;
    private Timestamp timestamp;
    private Coordinates topRightBorder;
    private Coordinates bottomLeftBorder;

    public UploadRoute(List<RoutePoint> points, Integer userId) {
        this.points = points;
        this.userId = userId;
    }

  public UploadRoute setBorders() {
        Double minLat = null;
        Double minLon = null;
        Double maxLat = null;
        Double maxLon = null;
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

    public UploadRoute setLengthsAndTime() {
        IntStream.range(1, points.size())
                .forEach(i -> {
                    RoutePoint curr = points.get(i);
                    RoutePoint prev = points.get(i - 1);
                    double t = Points.timeDiff(prev, curr);
                    double s = Points.between(prev, curr);
                    curr.setTravelTimeSeconds(t);
                    curr.setLength(s);
                    this.time += t;
                    this.length += s;
                    this.maxSpeed = (s > 0) ? Math.max(s / t, maxSpeed) : maxSpeed;
                });
        this.averageSpeed = (length > 0) ? this.length / this.time : 0;
        return this;
    }

    public UploadRoute setTimestamp(){
        this.timestamp = points.get(0).getTimestamp();
        return this;
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

    public double getTime() {
        return time;
    }

    public Timestamp getTimestamp() {
      return timestamp;
    }

    public double getLength() {
        return length;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

}
