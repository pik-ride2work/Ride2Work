package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Point;

import java.util.concurrent.Future;

public interface KafkaService {

    Future<Void> write(String string);

    Future<String> read(String string);

}
