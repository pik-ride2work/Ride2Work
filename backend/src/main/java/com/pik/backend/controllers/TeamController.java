package com.pik.backend.controllers;

import com.pik.backend.services.GenericController;
import com.pik.backend.services.GenericService;
import com.pik.backend.services.TeamService;
import com.pik.backend.util.RestInputValidator;
import com.pik.backend.util.TeamInputValidator;
import com.pik.backend.util.UserInputValidator;
import com.pik.ride2work.tables.pojos.Team;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.TeamRecord;
import org.jooq.DSLContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.pik.ride2work.Tables.*;


@Controller
public class TeamController {
    private final TeamService teamService;
    private final GenericController<Team, TeamRecord> genericController;

    public TeamController(DSLContext dsl, TeamService teamService) {
        this.teamService = teamService;
        GenericService<Team, TeamRecord> genericService = new GenericService<>(TEAM, Team.class, dsl, new TeamInputValidator());
        this.genericController = new GenericController<>(genericService);
    }

    @DeleteMapping("/teams/{id}")
    @CrossOrigin(origins = "localhost:4200")
    public ResponseEntity deleteTeam(@PathVariable Integer id) {
        return genericController.delete(id);
    }

    @PostMapping(value = "/teams", consumes = "application/json")
    @CrossOrigin(origins = "localhost:4200")
    public ResponseEntity createTeam(@RequestParam Team team) {
        return genericController.create(team);
    }

    @PutMapping(value = "/teams", consumes = "application/json")
    @CrossOrigin(origins = "localhost:4200")
    public ResponseEntity updateTeam(@RequestParam Team team) {
        return genericController.update(team, team.getId());
    }
}
