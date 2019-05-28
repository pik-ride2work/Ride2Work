package com.pik.backend.controllers;

import com.pik.backend.services.DefaultKafkaService;
import com.pik.backend.services.DefaultRouteService;
import com.pik.backend.services.RoutePoint;
import com.pik.ride2work.tables.pojos.Route;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("routes")
public class RouteController {
    private final DefaultRouteService routeService;
    private final DefaultKafkaService kafkaService;

    public RouteController(DefaultRouteService routeService, DefaultKafkaService kafkaService) {
        this.routeService = routeService;
        this.kafkaService = kafkaService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity create(@PathVariable Integer userId) {
        try {
            Integer routeId = routeService.startRoute(userId).get();
            return ResponseEntity
                    .ok()
                    .body(new JSONObject().put("routeId", routeId).toString());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            return Responses.internalError();
        }
    }

    @GetMapping("/points/{routeId}")
    public ResponseEntity getPointsByRouteId(@PathVariable Integer routeId) {
        try {
            List<RoutePoint> points = routeService.getPointsByRouteId(routeId).get();
            return ResponseEntity
                    .ok(points);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            return Responses.internalError();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity getRoutesByUserId(@PathVariable Integer userId) {
        try {
            List<Route> routes = routeService.getRoutesByUserId(userId).get();
            return ResponseEntity
                    .ok(routes);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            return Responses.internalError();
        }
    }

    @PostMapping("/write")
    public ResponseEntity writePoint(@RequestBody String point) {
        try {
            kafkaService.write(point).get();
            return ResponseEntity
                    .ok()
                    .build();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            return Responses.internalError();
        }
    }


}
