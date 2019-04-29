package com.pik.backend.controllers;

import com.pik.backend.services.DefaultKafkaService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class KafkaController {

    private final DefaultKafkaService kafkaService;

    public KafkaController(DefaultKafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }


    @PostMapping("/publish")
    public ResponseEntity write(@RequestParam(name = "string", required = true) String string) {
        try {
            kafkaService.write(string).get();
            return ResponseEntity
                    .ok()
                    .build();
        } catch (Exception e) {
            return Responses.internalError();
        }
    }

    @GetMapping("/subscribe")
    public ResponseEntity read() {
        try {
            kafkaService.read(null).get();
            return ResponseEntity
                    .ok()
                    .build();
        } catch (Exception e) {
            return Responses.internalError();
        }
    }

}