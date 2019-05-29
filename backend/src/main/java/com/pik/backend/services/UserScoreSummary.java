package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Route;
import com.pik.ride2work.tables.pojos.User;
import java.util.List;

public class UserScoreSummary {

  private final User user;
  private final double score;
  private final List<Route> routes;

  public UserScoreSummary(User user, double score, List<Route> routes) {
    this.user = user;
    this.score = score;
    this.routes = routes;
  }
}
