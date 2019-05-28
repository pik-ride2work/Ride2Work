package com.pik.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.backend.services.DefaultKafkaService;
import com.pik.backend.services.DefaultRouteService;
import com.pik.backend.services.RoutePoint;
import com.pik.backend.services.TeamScore;
import com.pik.ride2work.tables.pojos.Route;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("routes")
public class RouteController {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private final DefaultRouteService routeService;
  private final DefaultKafkaService kafkaService;

  public RouteController(DefaultRouteService routeService, DefaultKafkaService kafkaService) {
    this.routeService = routeService;
    this.kafkaService = kafkaService;
  }

  @PostMapping("/create/{userId}")
  public ResponseEntity startRoute(@PathVariable Integer userId) {
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


  @PostMapping("/end/{routeId}")
  public ResponseEntity endRoute(@PathVariable Integer routeId) {
    try {
      routeService.endRoute(routeId).get();
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

  @GetMapping("/score/{teamId}/{fromDate}/{toDate}/")
  public ResponseEntity getTeamScore(
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
      @PathVariable Integer teamId) {
    try {
      TeamScore teamScore = routeService.getTeamScore(teamId, fromDate, toDate).get();
      return ResponseEntity
          .ok()
          .body(objectMapper.writeValueAsString(teamScore));
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
      return Responses.serviceUnavailable();
    } catch (ExecutionException | JsonProcessingException e) {
      return Responses.internalError();
    }
  }

}
