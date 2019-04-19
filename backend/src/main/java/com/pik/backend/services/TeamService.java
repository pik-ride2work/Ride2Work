package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Team;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface TeamService extends GenericService<Team>{

    public List<Team> list();

    public Team getByName(String name);
}
