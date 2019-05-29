package com.pik.backend.controllers;

import com.pik.backend.services.MembershipService;
import com.pik.backend.services.NotFoundException;
import com.pik.ride2work.tables.pojos.Membership;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping
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

    @DeleteMapping
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

    @GetMapping("/{userId}")
    public ResponseEntity getByUserId(@PathVariable Integer userId) {
        try {
            Membership membership = membershipService.getCurrentMembershipByUserId(userId).get();
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

    @GetMapping("/list/{teamId}")
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

    @PutMapping("/changeOwner")
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
