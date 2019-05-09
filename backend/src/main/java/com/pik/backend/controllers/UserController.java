package com.pik.backend.controllers;

import com.pik.backend.services.DefaultUserService;
import com.pik.backend.services.NotFoundException;
import com.pik.ride2work.tables.pojos.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@Controller
@RequestMapping("users")
public class UserController {

    private final DefaultUserService userService;

    UserController(DefaultUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getByUsername(username).get();
            return ResponseEntity
                    .ok()
                    .body(user);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
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

    @PostMapping(consumes = "application/json")
    public ResponseEntity createUser(@RequestBody User user) {
        try {
            User newUser = userService.create(user).get();
            return ResponseEntity
                    .ok()
                    .body(newUser);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return Responses.badRequest(cause.getMessage());
            }
            return Responses.internalError();
        }
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.update(user).get();
            return ResponseEntity
                    .ok()
                    .body(updatedUser);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return Responses.badRequest(cause.getMessage());
            }
            return Responses.internalError();
        }
    }
}
