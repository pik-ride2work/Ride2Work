package com.pik.backend.controllers;

import com.pik.backend.services.GenericController;
import com.pik.backend.services.TeamService;
import com.pik.ride2work.tables.pojos.Team;
import com.pik.ride2work.tables.records.TeamRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeamController {
    private final TeamService teamService;
    private final GenericController<Team, TeamRecord> teamController;

    public TeamController(TeamService teamService, GenericController<Team, TeamRecord> teamController) {
        this.teamService = teamService;
        this.teamController = teamController;
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity deleteTeam(@PathVariable Integer id) {
        return teamController.delete(id);
    }

    @PostMapping(value = "/teams", consumes = "application/json")
    public ResponseEntity createTeam(@RequestParam Team team) {
        return teamController.create(team);
    }

    @PutMapping(value = "/teams", consumes = "application/json")
    public ResponseEntity updateTeam(@RequestParam Team team) {
        return teamController.update(team, team.getId());
    }
}
