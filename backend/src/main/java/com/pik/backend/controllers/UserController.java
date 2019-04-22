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
      return ResponseEntity.
          ok()
          .body(user);
    } catch (Exception e) {
      return ResponseEntity
          .badRequest()
          .body(ErrorResponse.error(e));
    }
  }

  @PostMapping(value = "/users", consumes = "application/json")
  public ResponseEntity createUser(@RequestBody User user) {
    try {
      User newUser = userService.create(user).get();
      return ResponseEntity
          .ok()
          .body(newUser);
    } catch (Exception e) {
      return ResponseEntity
          .badRequest()
          .body(ErrorResponse.error(e));
    }
  }

  @PutMapping(value = "/users", consumes = "application/json")
  public ResponseEntity updateUser(@RequestBody User user) {
    try {
      User updatedUser = userService.update(user).get();
      return ResponseEntity
          .ok()
          .body(updatedUser);
    } catch (Exception e) {
      return ResponseEntity
          .badRequest()
          .body(ErrorResponse.error(e));
    }
  }
}
