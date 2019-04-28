package com.pik.backend.controllers;

import com.pik.backend.services.DefaultUserService;
import com.pik.backend.services.NotFoundException;
import com.pik.ride2work.tables.pojos.User;

import java.util.concurrent.ExecutionException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    private final DefaultUserService userService;

    UserController(DefaultUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getByUsername(username).get();
            return ResponseEntity
                    .ok()
                    .body(user);
        } catch (ExecutionException | InterruptedException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return Responses.badRequest(cause.getMessage());
            }
            if (cause instanceof NotFoundException) {
                return Responses.notFound();
            }
            return Responses.internalError();
        }
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public ResponseEntity createUser(@RequestBody User user) {
        try {
            User newUser = userService.create(user).get();
            return ResponseEntity
                    .ok()
                    .body(newUser);
        } catch (ExecutionException | InterruptedException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return Responses.badRequest(cause.getMessage());
            }
            return Responses.internalError();
        }
    }

    @PutMapping(value = "/users", consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.update(user).get();
            return ResponseEntity
                    .ok()
                    .body(updatedUser);
        } catch (ExecutionException | InterruptedException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return Responses.badRequest(cause.getMessage());
            }
            return Responses.internalError();
        }
    }
}
