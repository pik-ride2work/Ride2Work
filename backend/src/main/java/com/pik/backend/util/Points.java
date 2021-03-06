package com.pik.backend.util;

import com.pik.backend.custom_daos.Coordinates;
import com.pik.backend.services.RoutePoint;

import java.sql.Timestamp;

/**
 * All results are represented in meters.
 */
public class Points {
    private Points() {
    }

    public static final double MAX_LON = 180.0;
    public static final double MIN_LON = -180.0;
    public static final double MAX_LAT = 90.0;
    public static final double MIN_LAT = -90.0;
    private static final int R = 6371; // Radius of the earth

    /**
     * @return distance between 2 points in meters
     */
    public static double between(RoutePoint one, RoutePoint two) {
        Coordinates coordOne = one.getCoordinates();
        Coordinates coordTwo = two.getCoordinates();
        double lat1 = coordOne.getLatitude();
        double lon1 = coordOne.getLongitude();
        double el1 = coordOne.getLatitude();
        double lat2 = coordTwo.getLatitude();
        double lon2 = coordTwo.getLongitude();
        double el2 = coordTwo.getLatitude();

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * @return time difference between two points in seconds
     */
    public static double timeDiff(RoutePoint one, RoutePoint two) {
        Timestamp tsOne = one.getTimestamp();
        Timestamp tsTwo = two.getTimestamp();
        return (double) (tsTwo.getTime() - tsOne.getTime()) / 1_000;
    }

}
