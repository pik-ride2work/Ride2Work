package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Team;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface TeamService {
    public void delete(Integer id);

    public Team create(Team team);

    public Team update(Team team);

    public List<Team> list();

    public Team getByName(String name);
}
