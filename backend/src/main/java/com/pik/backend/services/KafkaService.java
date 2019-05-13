package com.pik.backend.services;

import java.util.concurrent.Future;

public interface KafkaService {

    Future<Void> readPoint(String string);

    Future<Void> readJson(String json);
}
