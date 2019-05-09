package com.pik.backend.controllers;

import com.pik.backend.services.MembershipService;
import com.pik.backend.services.NotFoundException;
import com.pik.ride2work.tables.pojos.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/membership")
    public ResponseEntity joinTeam(@RequestParam(name = "userId", required = true) Integer userId,
                                   @RequestParam(name = "teamId", required = true) Integer teamId) {
        try {
            Membership createdMembership = membershipService.joinTeam(userId, teamId).get();
            return ResponseEntity
                    .ok()
                    .body(createdMembership);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalStateException) {
                return Responses.badRequest(cause.getMessage());
            }
            return Responses.internalError();
        }
    }

    @DeleteMapping("/membership")
    public ResponseEntity leaveTeam(@RequestParam(name = "userId", required = true) Integer userId) {
        try {
            membershipService.leaveTeam(userId).get();
            return ResponseEntity
                    .ok()
                    .build();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return Responses.serviceUnavailable();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalStateException) {
                return Responses.badRequest(cause.getMessage());
            }
            if (cause instanceof NotFoundException) {
                return Responses.notFound();
            }
            return Responses.internalError();
        }
    }

    @GetMapping("/membership/{userId}")
    public ResponseEntity getByUserId(@PathVariable Integer userId) {
        try {
            Membership membership = membershipService.getByUserId(userId).get();
            return ResponseEntity
                    .ok(membership);
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

    @GetMapping("/membership/list/{teamId}")
    public ResponseEntity getTeamMemberships(@PathVariable Integer teamId) {
        try {
            List<Membership> memberships = membershipService.getMembers(teamId).get();
            return ResponseEntity
                    .ok(memberships);
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

    @PutMapping("/membership/change")
    public ResponseEntity changeOwner(@RequestParam(name = "ownerId", required = true) Integer userId,
                                      @RequestParam(name = "teamId", required = true) Integer teamId) {
        try {
            membershipService.changeOwner(userId, teamId).get();
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
            if (cause instanceof IllegalStateException) {
                return Responses.badRequest(cause.getMessage());
            }
            return Responses.internalError();
        }
    }
}
