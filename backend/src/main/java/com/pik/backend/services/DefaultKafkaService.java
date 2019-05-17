package com.pik.backend.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.DSLContext;
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

    public DefaultKafkaService(ObjectMapper objectMapper, DefaultRouteService routeService, DSLContext dsl, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.routeService = routeService;
        this.dsl = dsl;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @KafkaListener(topics = "point", groupId = "group-id")
    public Future<Void> readPoint(String input) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        RoutePoint routePoint = null;
        RoutePoint point = RoutePoint.of(input);
        try {
            routePoint = point;
        } catch (IllegalArgumentException e) {
            future.completeExceptionally(e);
            return future;
        }
        try{
            routeService.writeSinglePoint(point).get();
            future.complete(null);
        } catch (Exception e){
            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public Future<Void> write(String point) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        kafkaTemplate.send("test", point);
        future.complete(null);
        return future;
    }

    @Override
    @KafkaListener(topics = "test", groupId = "group-id")
    public Future<Void> readJson(String json) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        UploadRoute uploadRoute = null;
        try {
            uploadRoute = objectMapper.readValue(json, UploadRoute.class)
                    .setBorders()
                    .setLengthsAndTime();
            routeService.writeUploadedRoute(uploadRoute).get();
        } catch (Exception e) {
            future.completeExceptionally(new IOException("Failed to upload the route."));
        }
        return future;
    }


}
