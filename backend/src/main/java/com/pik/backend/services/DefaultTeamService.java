package com.pik.backend.services;

import static com.pik.ride2work.Tables.MEMBERSHIP;
import static com.pik.ride2work.Tables.TEAM;
import static com.pik.ride2work.Tables.USER;

import com.pik.backend.util.DSLWrapper;
import com.pik.backend.util.TeamInputValidator;
import com.pik.backend.util.Validated;
import com.pik.ride2work.tables.daos.TeamDao;
import com.pik.ride2work.tables.pojos.Team;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.TeamRecord;
import com.pik.ride2work.tables.records.UserRecord;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultTeamService implements TeamService {

    private final TeamInputValidator validator = new TeamInputValidator();
    private final DSLContext dsl;

    public DefaultTeamService(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Future<User> getTeamOwner(Integer teamId) {
        CompletableFuture<User> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            UserRecord record = DSL.using(cfg)
                    .selectFrom(USER)
                    .where(USER.ID.eq(DSL.using(cfg)
                            .select(MEMBERSHIP.ID_USER)
                            .from(MEMBERSHIP)
                            .where(MEMBERSHIP.ID_TEAM.eq(teamId)
                                    .and(MEMBERSHIP.IS_OWNER)
                                    .and(MEMBERSHIP.IS_PRESENT))))
                    .fetchOne();
            if (record == null) {
                future.completeExceptionally(new NotFoundException("No owner/team found."));
                return;
            }
          User teamOwner = record.into(User.class);
          future.complete(NoCredUserView.apply(teamOwner));
        });
        return future;
    }

    @Override
    public Future<Void> delete(Integer teamId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            try {
                DSL.using(cfg)
                        .deleteFrom(TEAM)
                        .where(TEAM.ID.eq(teamId));
            } catch (Exception e) {
                future.completeExceptionally(new NotFoundException("Team not found"));
                return;
            }
            DSL.using(cfg)
                    .update(MEMBERSHIP)
                    .set(MEMBERSHIP.END, Timestamp.from(Instant.now()))
                    .set(MEMBERSHIP.IS_PRESENT, false)
                    .where(MEMBERSHIP.ID_TEAM.eq(teamId))
                    .execute();
            future.complete(null);
        });
        return future;
    }

    @Override
    public Future<Team> create(Team team, Integer ownerId) {
        CompletableFuture<Team> future = new CompletableFuture<>();
        Validated validation = validator.validateRegistrationInput(team);
        if (!validation.isValid()) {
            future.completeExceptionally(validation.getCause());
            return future;
        }
        DSLWrapper.transaction(dsl, future, cfg -> {
            if (DefaultMembershipService.doesUserHasTeam(ownerId, cfg, dsl)) {
                future.completeExceptionally(new IllegalStateException("User is a member of a team already"));
                return;
            }
            TeamRecord teamRecord = DSL.using(cfg)
                .insertInto(TEAM, TEAM.NAME, TEAM.MEMBER_COUNT)
                .values(team.getName(), 1)
                .returning(TEAM.fields())
                .fetchOne();
            if(teamRecord == null){
                future.completeExceptionally(new NotFoundException("Team record not found."));
                return;
            }
            Team newTeam = teamRecord.into(Team.class);
            DSL.using(cfg)
                    .insertInto(MEMBERSHIP, MEMBERSHIP.START, MEMBERSHIP.ID_TEAM, MEMBERSHIP.ID_USER, MEMBERSHIP.IS_OWNER)
                    .values(Timestamp.from(Instant.now()), newTeam.getId(), ownerId, Boolean.TRUE)
                    .execute();
            future.complete(newTeam);
        });
        return future;
    }

    @Override
    public Future<Team> update(Team team) {
        CompletableFuture<Team> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            TeamRecord teamRecord = DSL.using(cfg)
                    .update(TEAM)
                    .set(dsl.newRecord(TEAM, team))
                    .where(TEAM.ID.eq(team.getId()))
                    .returning()
                    .fetchOne();
            if (teamRecord == null) {
                future.completeExceptionally(new NotFoundException("Team not found."));
                return;
            }
            future.complete(teamRecord.into(Team.class));
        });
        return future;
    }

    @Override
    public Future<List<Team>> list() {
        CompletableFuture<List<Team>> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            List<Team> result = DSL.using(cfg)
                    .selectFrom(TEAM)
                    .fetch()
                    .into(Team.class);
            future.complete(result);
        });
        return future;
    }

    @Override
    public Future<Team> getByName(String name) {
        CompletableFuture<Team> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            TeamRecord teamRecord = DSL.using(cfg)
                    .selectFrom(TEAM)
                    .where(TEAM.NAME.eq(name))
                    .fetchOne();
            if (teamRecord == null) {
                future.completeExceptionally(new NotFoundException("Team Not Found"));
                return;
            }
            future.complete(teamRecord.into(Team.class));
        });
        return future;
    }

    @Override
    public Future<Team> getById(Integer id) {
        CompletableFuture<Team> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            TeamDao teamDao = new TeamDao(cfg);
            Team team = teamDao.fetchOneById(id);
            if (team == null) {
                future.completeExceptionally(new NotFoundException("Team Not Found"));
                return;
            }
            future.complete(team);
        });
        return future;
    }

    @Override
    public Future<List<User>> getUserList(Integer teamId) {
        CompletableFuture<List<User>> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            Result<UserRecord> records = DSL.using(cfg)
                    .selectFrom(USER)
                    .where(USER.ID.in(DSL.using(cfg)
                            .select(MEMBERSHIP.ID_USER)
                            .from(MEMBERSHIP)
                            .where(MEMBERSHIP.IS_PRESENT)
                            .and(MEMBERSHIP.ID_TEAM.eq(teamId))))
                    .fetch();
            if (records == null) {
                future.completeExceptionally(new NotFoundException("Team/users not found."));
                return;
            }
            List<User> users = records.into(User.class);
            future.complete(NoCredUserView.apply(users));
        });
        return future;
    }
}
