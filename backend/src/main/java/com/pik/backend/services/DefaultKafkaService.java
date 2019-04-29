package com.pik.backend.services;

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
        System.out.println("Received msg: " + string);
        future.complete(string);
        return future;
    }



}
