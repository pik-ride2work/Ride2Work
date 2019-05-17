package com.pik.backend.services;

import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.Future;

public interface KafkaService {

    @KafkaListener(topics = "point", groupId = "group-id")
    Future<Void> readPoint(String point);

    Future<Void> write(String point);

    @KafkaListener(topics = "test", groupId = "group-id")
    Future<Void> readJson(String json);
}
