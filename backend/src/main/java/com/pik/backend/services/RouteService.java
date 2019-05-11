package com.pik.backend.services;

import java.util.concurrent.Future;

public interface RouteService {
    Future<Integer> startRoute(Integer userId);

    Future<Void> endRoute(Integer routeId);

    Future<Void> writeUploadedRoute(UploadedRoute uploadedRoute);
}
