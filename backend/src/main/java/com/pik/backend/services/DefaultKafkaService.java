package com.pik.backend.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.backend.custom_daos.Coordinates;
import com.pik.backend.util.DSLWrapper;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


@Repository
public class DefaultKafkaService implements KafkaService {

    private final ObjectMapper objectMapper;
    private final DefaultRouteService routeService;
    private final DSLContext dsl;
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String INSERT_POINT_QUERY_TEMPLATE = "INSERT INTO ride2work.point (timestamp, coordinates) " +
            "VALUES ('%s', ST_GeographyFromText('SRID=4326;POINT(%s %s)'))";

    public DefaultKafkaService(ObjectMapper objectMapper, DefaultRouteService routeService, DSLContext dsl, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.routeService = routeService;
        this.dsl = dsl;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Future<Void> write(String point) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        kafkaTemplate.send("test", point);
        future.complete(null);
        return future;
    }

    @Override
    @KafkaListener(topics = "point", groupId = "group-id")
    public Future<Void> readPoint(String point) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        RoutePoint routePoint = null;
        try {
            routePoint = RoutePoint.of(point);
        } catch (IllegalArgumentException e) {
            future.completeExceptionally(e);
            return future;
        }
        RoutePoint finalRoutePoint = routePoint;
        DSLWrapper.transaction(dsl, future, cfg -> {
            try {
                Coordinates coordinates = finalRoutePoint.getCoordinates();
                DSL.using(cfg)
                        .execute(String.format(INSERT_POINT_QUERY_TEMPLATE,
                                finalRoutePoint.getTimestamp().toString(),
                                coordinates.getLongitude(),
                                coordinates.getLatitude()));
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    @Override
    @KafkaListener(topics = "json", groupId = "group-id")
    public Future<Void> readJson(String json) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        UploadRoute uploadRoute = null;
        try {
            uploadRoute = objectMapper.readValue(json, UploadRoute.class);
            routeService.writeUploadedRoute(uploadRoute).get();
        } catch (Exception e) {
            future.completeExceptionally(new IOException("Failed to upload the route."));
        }
        return future;
    }


}
