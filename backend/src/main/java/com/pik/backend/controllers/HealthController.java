package com.pik.backend.controllers;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthController {

    @GetMapping("/health")
    @ResponseBody
    public ResponseEntity<JSONObject> getHealth() {
        return ResponseEntity
                .ok()
                .body(new JSONObject()
                        .put("healthy", true));
    }

}
