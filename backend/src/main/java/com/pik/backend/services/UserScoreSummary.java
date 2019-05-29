package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Route;
import com.pik.ride2work.tables.pojos.User;
import java.util.List;

public class UserScoreSummary {

  public final User user;
  public final double score;
  public final List<Route> routes;

  public UserScoreSummary(User user, double score, List<Route> routes) {
    this.user = user;
    this.score = score;
    this.routes = routes;
  }
}
