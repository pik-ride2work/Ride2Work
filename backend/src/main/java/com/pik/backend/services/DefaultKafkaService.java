package com.pik.backend.services;

import com.pik.backend.util.DSLWrapper;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


@Repository
public class DefaultKafkaService implements KafkaService {

    private final DSLContext dsl;
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String INSERT_POINT_QUERY_TEMPLATE = "INSERT INTO ride2work.point (timestamp, coordinates) " +
            "VALUES ('%s', ST_GeographyFromText('SRID=4326;POINT(%s %s)'))";

    public DefaultKafkaService(DSLContext dsl, KafkaTemplate<String, String> kafkaTemplate) {
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
    @KafkaListener(topics = "test", groupId = "group-id")
    public Future<Void> read(String point) {
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
                DSL.using(cfg)
                        .execute(String.format(INSERT_POINT_QUERY_TEMPLATE,
                                finalRoutePoint.getTimestamp().toString(),
                                finalRoutePoint.getLongitude(),
                                finalRoutePoint.getLatitude()));
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }


}
