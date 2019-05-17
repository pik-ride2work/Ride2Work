package com.pik.backend.services;

import java.util.List;
import java.util.concurrent.Future;

public interface RouteService {
    Future<List<RoutePoint>> getPointsByRouteId(Integer routeId);

    Future<Integer> startRoute(Integer userId);

    Future<Void> endRoute(Integer routeId);

    Future<Void> writeUploadedRoute(UploadRoute uploadRoute);

    Future<Void> writeSinglePoint(RoutePoint point);
}
