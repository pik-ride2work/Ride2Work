package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Route;
import java.util.List;

public class TeamScore {
  private final List<UserScoreSummary> users;
  private final List<Route> routes;

  public TeamScore(List<UserScoreSummary> users, List<Route> routes) {
    this.users = users;
    this.routes = routes;
  }
}
