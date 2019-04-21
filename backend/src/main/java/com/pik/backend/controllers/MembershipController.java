package com.pik.backend.controllers;

import com.pik.backend.services.MembershipService;
import com.pik.ride2work.tables.pojos.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    } catch (Exception e) {
      return ResponseEntity
          .badRequest()
          .body(ErrorResponse.error(e));
    }
  }

  @DeleteMapping("/membership")
  public ResponseEntity leaveTeam(@RequestParam(name = "userId", required = true) Integer userId) {
    try {
      membershipService.leaveTeam(userId).get();
      return ResponseEntity
          .ok()
          .build();
    } catch (Exception e) {
      return ResponseEntity
          .badRequest()
          .body(ErrorResponse.error(e));
    }
  }
}
