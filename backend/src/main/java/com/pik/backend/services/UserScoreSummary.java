package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Route;
import com.pik.ride2work.tables.pojos.User;
import java.util.Date;
import java.util.List;

public class UserScoreSummary {
  private final Date from;
  private final Date to;
  private final User user;
  private final Integer score;
  private final List<Route> routes;

  public UserScoreSummary(Date from, Date to, User user, Integer score, List<Route> routes) {
    this.from = from;
    this.to = to;
    this.user = user;
    this.score = score;
    this.routes = routes;
  }
}
