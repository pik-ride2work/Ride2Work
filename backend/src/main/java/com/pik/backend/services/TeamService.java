package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Team;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface TeamService {

  public Future<Void> delete(Integer teamId);

  public Future<Team> create(Team team, Integer ownerId);

  public Future<Team> update(Team team);

  public Future<List<Team>> list();

  public Future<Team> getByName(String name);
}
