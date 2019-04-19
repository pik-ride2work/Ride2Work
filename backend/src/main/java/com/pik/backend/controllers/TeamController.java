package com.pik.backend.controllers;

import com.pik.backend.services.GenericController;
import com.pik.backend.services.TeamService;
import com.pik.ride2work.tables.pojos.Team;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeamController {
    private final TeamService teamService;
    private final GenericController<Team> teamController;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
        this.teamController = new GenericController<>(teamService);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity deleteTeam(@PathVariable Integer id) {
        return teamController.
    }

    @PostMapping(value = "/teams", consumes = "application/json")
    public ResponseEntity createTeam(@RequestParam Team team) {
        return teamController.create(team);
    }

    @PutMapping(value = "/teams", consumes = "application/json")
    public ResponseEntity updateTeam(@RequestParam Team team) {
        return teamController.update(team);
    }
}
