package com.pik.backend.controllers;

import com.pik.backend.services.DefaultKafkaService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class KafkaController {

    private final DefaultKafkaService kafkaService;
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaController(KafkaTemplate<String, String> kafkaTemplate, DefaultKafkaService kafkaService) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaService = kafkaService;
    }

    private static final String TOPIC = "test";

    @PostMapping("/publish")
    public String write(@RequestParam(name = "string", required = true) String string) {
        kafkaTemplate.send(TOPIC,string);
        return "Published";

    }

    @GetMapping("/subscribe")
    public String read() {
        return null;
    }

}