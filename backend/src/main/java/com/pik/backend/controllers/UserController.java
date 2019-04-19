package com.pik.backend.controllers;

import com.pik.backend.services.DefaultUserService;
import com.pik.backend.services.GenericController;
import com.pik.backend.services.UserService;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin(origins = "localhost:4200")
public class UserController {
    private final GenericController<User, UserRecord> userController;
    private final UserService userService;

    UserController(GenericController<User, UserRecord> userController, UserService userService) {
        this.userController = userController;
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
        return userController.create(user);
    }

    @PutMapping(value = "/users", consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody User user) {
        return userController.update(user, user.getId());
    }
}
