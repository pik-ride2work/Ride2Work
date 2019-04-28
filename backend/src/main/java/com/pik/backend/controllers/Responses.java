package com.pik.backend.controllers;

import org.springframework.http.ResponseEntity;

public class Responses {
    public static ResponseEntity internalError(){
        return ResponseEntity
                .status(500)
                .build();
    }

    public static ResponseEntity badRequest(String message){
        return ResponseEntity
                .badRequest()
                .body(message);
    }

    public static ResponseEntity notFound(){
        return ResponseEntity
                .badRequest()
                .build();
    }

    public static ResponseEntity ok(){
        return ResponseEntity
                .ok()
                .build();
    }
}
