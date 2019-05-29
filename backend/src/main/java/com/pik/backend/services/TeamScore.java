package com.pik.backend.services;

import java.util.List;

public class TeamScore {
  public final List<UserScoreSummary> users;

  public TeamScore(List<UserScoreSummary> users) {
    this.users = users;
  }

}
