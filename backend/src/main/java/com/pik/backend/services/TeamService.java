package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Team;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface TeamService {
    public void delete(Integer id);

    public Future<Team> create(Team team, Integer ownerId);

    public Team update(Team team);

    public List<Team> list();

    public Team getByName(String name);
}
