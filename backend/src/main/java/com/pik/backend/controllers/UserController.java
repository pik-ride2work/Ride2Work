package com.pik.backend.controllers;

import com.pik.backend.services.GenericController;
import com.pik.backend.services.GenericService;
import com.pik.backend.services.UserService;
import com.pik.backend.util.UserInputValidator;
import com.pik.ride2work.Tables;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    private final UserService userService;
    private final GenericController<User, UserRecord> genericController;

    UserController(DSLContext dsl,  UserService userService) {
        this.userService = userService;
        GenericService<User, UserRecord> genericService = new GenericService<>(Tables.USER, User.class, dsl, new UserInputValidator());
        this.genericController = new GenericController<>(genericService);
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
        return genericController.create(user);
    }

    @PutMapping(value = "/users", consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody User user) {
        return genericController.update(user, user.getId());
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        return genericController.delete(id);
    }
}
