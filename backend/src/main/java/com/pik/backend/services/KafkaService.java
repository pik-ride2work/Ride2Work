package com.pik.backend.services;

import java.util.concurrent.Future;

public interface KafkaService {

    Future<Void> write(String string);

    Future<Void> read(String string);

}
