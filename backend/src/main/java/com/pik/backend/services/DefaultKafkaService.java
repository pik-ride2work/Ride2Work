package com.pik.backend.services;

import com.pik.backend.util.DSLWrapper;
import com.pik.ride2work.tables.pojos.Point;
import com.pik.ride2work.tables.records.PointRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static com.pik.ride2work.Tables.POINT;


@Repository
public class DefaultKafkaService implements KafkaService {

    private final DSLContext dsl;
    private KafkaTemplate<String, String> kafkaTemplate;

    public DefaultKafkaService(DSLContext dsl, KafkaTemplate<String, String> kafkaTemplate) {
        this.dsl = dsl;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Future<Void> write(String string) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        kafkaTemplate.send("test",string);
        future.complete(null);
        return future;
    }

    @Override
    @KafkaListener(topics = "test", groupId = "group-id")
    public Future<String> read(String string) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String[] parts = string.split("/");

        DSLWrapper.transaction(dsl, future, cfg -> {
                DSL.using(cfg)
                        .fetch(String.format("INSERT INTO ride2work.point (timestamp, coordinates) " +
                                        "VALUES ('%s', ST_GeographyFromText('SRID=4326;POINT(%s %s)'))"
                                ,  parts[1], parts[2], parts[3]));
            future.complete(string);
                });


        return future;
    }



}
