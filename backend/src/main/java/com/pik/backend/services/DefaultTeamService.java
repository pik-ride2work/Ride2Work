package com.pik.backend.services;

import static com.google.common.util.concurrent.Callables.returning;
import static com.pik.ride2work.Tables.TEAM;

import com.pik.backend.util.NotFoundException;
import com.pik.backend.util.TeamInputValidator;
import com.pik.backend.util.Validated;
import com.pik.ride2work.tables.pojos.Team;

import com.pik.ride2work.tables.records.TeamRecord;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultTeamService implements TeamService {

  private final TeamInputValidator validator = new TeamInputValidator();
  private final DSLContext dsl;

  public DefaultTeamService(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void delete(Integer id) {

  }

  @Override
  public Future<Team> create(Team team, Integer ownerId) {
    CompletableFuture<Team> future = new CompletableFuture<>();
    Validated validation = validator.validateCreateInput(team);
    if (!validation.isValid()) {
      future.completeExceptionally(validation.getCause());
    }
    dsl.transaction(cfg -> {
      boolean userHasATeamAlready = DSL.using(cfg)
          .fetchExists(dsl.selectOne().from(TEAM)
              .where(TEAM.ID.eq(ownerId)));
      if (userHasATeamAlready) {
        future.completeExceptionally(new NotFoundException("User already has a team."));
      }
      TeamRecord teamRecord = DSL.using(cfg)
          .insertInto(TEAM)
          .set(dsl.newRecord(TEAM, team))
          .set(TEAM.MEMBER_COUNT, 1)
          .returning(TEAM.fields())
          .fetchOne();
      future.complete(teamRecord.into(Team.class));
    });
    return future;
  }

  @Override
  public Team update(Team team) {
    return null;
  }

  @Override
  public List<Team> list() {
    return null;
  }

  @Override
  public Team getByName(String name) {
    return null;
  }
}
