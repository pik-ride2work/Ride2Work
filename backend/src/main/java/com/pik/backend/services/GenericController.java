package com.pik.backend.services;

import org.jooq.Record;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class GenericController<T, R extends Record> {
    private final GenericService<T, R> service;

    public GenericController(GenericService<T, R> service) {
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

    public ResponseEntity update(T input, Integer id) {
        T updatedObject = null;
        try {
            updatedObject = service.update(input, id);
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
