package com.pik.backend.services;

import java.util.List;

public class TeamScore {
  private final List<UserScoreSummary> users;

  public TeamScore(List<UserScoreSummary> users) {
    this.users = users;
  }
}
