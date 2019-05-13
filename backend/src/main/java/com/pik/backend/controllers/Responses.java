package com.pik.backend.controllers;

import org.springframework.http.ResponseEntity;

public class Responses {
    private Responses() {
    }

    public static ResponseEntity internalError() {
        return ResponseEntity
                .status(500)
                .build();
    }

    public static ResponseEntity badRequest(String message) {
        return ResponseEntity
                .badRequest()
                .body(message);
    }

    public static ResponseEntity notFound() {
        return ResponseEntity
                .notFound()
                .build();
    }

    public static ResponseEntity serviceUnavailable() {
        return ResponseEntity
                .status(503)
                .body("Service unavailable.");

    }

    public static ResponseEntity conflict(String msg) {
        return ResponseEntity
                .status(409)
                .body(String.format("Operation is not available: %s", msg));
    }

}
