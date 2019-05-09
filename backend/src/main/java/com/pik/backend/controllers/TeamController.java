package com.pik.backend.controllers;

import com.pik.backend.services.DefaultTeamService;
import com.pik.backend.services.NotFoundException;
import com.pik.ride2work.tables.pojos.Team;
import com.pik.ride2work.tables.pojos.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @PutMapping("/teams")
    public ResponseEntity update(@RequestBody Team team) {
        try {
            Team updatedTeam = teamService.update(team).get();
            return ResponseEntity
                    .ok(updatedTeam);
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

    @GetMapping("/teams/all")
    public ResponseEntity list() {
        try {
            List<Team> list = teamService.list().get();
            return ResponseEntity
                    .ok(list);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            return Responses.internalError();
        }
    }

    @GetMapping("/teams/byName/{name}")
    public ResponseEntity getByName(@PathVariable String name) {
        try {
            Team team = teamService.getByName(name).get();
            return ResponseEntity
                    .ok(team);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NotFoundException) {
                return Responses.notFound();
            }
            return Responses.internalError();
        }
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        try {
            Team team = teamService.getById(id).get();
            return ResponseEntity
                    .ok(team);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NotFoundException) {
                return Responses.notFound();
            }
            return Responses.internalError();
        }
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            teamService.delete(id).get();
            return ResponseEntity
                    .ok()
                    .build();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NotFoundException) {
                return Responses.notFound();
            }
            return Responses.internalError();
        }
    }

    @GetMapping("/teams/owner/{teamId}")
    public ResponseEntity getOwner(@PathVariable Integer teamId) {
        try {
            User user = teamService.getTeamOwner(teamId).get();
            return ResponseEntity
                    .ok(user);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NotFoundException) {
                return Responses.notFound();
            }
            return Responses.internalError();
        }
    }

    @GetMapping("/teams/listUsers/{teamId")
    public ResponseEntity listUsers(@PathVariable Integer teamId) {
        try {
            List<User> users = teamService.getUserList(teamId).get();
            return ResponseEntity
                    .ok(users);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NotFoundException) {
                return Responses.notFound();
            }
            return Responses.internalError();
        }
    }

}
