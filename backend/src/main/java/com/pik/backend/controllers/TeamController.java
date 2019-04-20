package com.pik.backend.controllers;

import com.pik.backend.services.DefaultTeamService;
import com.pik.backend.util.NotFoundException;
import com.pik.ride2work.tables.pojos.Team;
import com.pik.ride2work.tables.pojos.User;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TeamController {

  private final DefaultTeamService teamService;

  public TeamController(DefaultTeamService teamService) {
    this.teamService = teamService;
  }

  @PostMapping("/teams/{ownerID}")
  public ResponseEntity create(@RequestBody Team team, @PathVariable Integer ownerID) {
    try {
      Team createdTeam = teamService
          .create(team, ownerID)
          .get();
      return ResponseEntity
          .ok(createdTeam);
    } catch (Exception e) {
      return ResponseEntity
          .badRequest()
          .body(ErrorResponse.error(e));
    }
  }


}
