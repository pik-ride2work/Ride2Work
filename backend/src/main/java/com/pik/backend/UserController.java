package com.pik.backend;

import com.pik.backend.service_impl.DefaultUserService;
import com.pik.ride2work.tables.pojos.User;
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
        User user = null;
        try {
            user = userService.getByUsername(username);
        } catch (DataAccessException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        if (user == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.
                ok()
                .body(user);
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public ResponseEntity createUser(@RequestBody User user) {
        User newUser = null;
        try {
            newUser = userService.create(user);
        } catch (DataAccessException | IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity
                .ok()
                .body(newUser);
    }

    @PutMapping(value = "/users/{id}", consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody User user, @RequestParam Integer id) {
        User updatedUser = null;
        try {
            updatedUser = userService.update(user, id);
        } catch (DataAccessException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity
                .ok()
                .body(updatedUser);
    }
}
