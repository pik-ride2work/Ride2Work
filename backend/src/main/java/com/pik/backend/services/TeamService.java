package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.Team;

import java.util.concurrent.Future;

import com.pik.ride2work.tables.pojos.User;

import java.util.List;

public interface TeamService {

    Future<User> getTeamOwner(Integer teamId);

    public Future<Void> delete(Integer teamId);

    public Future<Team> create(Team team, Integer ownerId);

    public Future<Team> update(Team team);

    public Future<List<Team>> list();

    public Future<Team> getByName(String name);

    Future<Team> getById(Integer id);

    Future<List<User>> getUserList(Integer teamId);
}
