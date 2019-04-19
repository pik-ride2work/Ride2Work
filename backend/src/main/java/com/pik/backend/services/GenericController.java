package com.pik.backend.services;

import com.pik.ride2work.Tables;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

public class GenericController<T> {
    private final GenericService<T> service;

    public GenericController(GenericService<T> service) {
        this.service = service;
    }

    public ResponseEntity create(T input) {
        T newObject = null;
        try {
            newObject = service.create(input);
        } catch (DataAccessException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity
                .ok()
                .body(newObject);
    }

    public ResponseEntity update(T input) {
        T updatedObject = null;
        try {
            updatedObject = service.update(input);
        } catch (DataAccessException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        if (updatedObject == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(updatedObject);
    }

    public ResponseEntity delete(Integer id) {
        try {
            service.delete(id);
        } catch (DataAccessException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity
                .ok()
                .build();
    }

}
