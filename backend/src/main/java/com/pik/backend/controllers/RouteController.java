package com.pik.backend.controllers;

import com.pik.backend.services.DefaultRouteService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("route")
public class RouteController {
    private final DefaultRouteService routeService;

    public RouteController(DefaultRouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity create(@PathVariable Integer userId) {
        try {
            Integer routeId = routeService.startRoute(userId).get();
            return ResponseEntity
                    .ok(new JSONObject().put("routeId", routeId));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            return Responses.internalError();
        }
    }
}
