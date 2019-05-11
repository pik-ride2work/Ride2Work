package com.pik.backend.util;

import com.pik.backend.services.RoutePoint;

/**
 * All results are represented in meters.
 */
public class Distance {
    private static final int R = 6371; // Radius of the earth

    public static double between(RoutePoint one, RoutePoint two) {
        double lat1 = one.getLatitude();
        double lon1 = one.getLongitude();
        double el1 = one.getLatitude();
        double lat2 = two.getLatitude();
        double lon2 = two.getLongitude();
        double el2 = two.getLatitude();

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

}
