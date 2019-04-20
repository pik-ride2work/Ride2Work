package com.pik.backend.services;

import static com.pik.ride2work.Tables.MEMBERSHIP;
import static com.pik.ride2work.Tables.TEAM;

import com.pik.backend.util.DSLWrapper;
import com.pik.ride2work.tables.pojos.Membership;
import com.pik.ride2work.tables.records.MembershipRecord;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultMembershipService implements MembershipService {

  private final DSLContext dsl;

  public DefaultMembershipService(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public Future<Membership> joinTeam(Integer userId, Integer teamId) {
    CompletableFuture<Membership> future = new CompletableFuture<>();
    DSLWrapper.transaction(dsl, future, cfg -> {
      if (doesUserHasTeam(userId, cfg, dsl)) {
        throw new IllegalStateException("User is a member of a team already");
      }
      DSL.using(cfg)
          .update(TEAM)
          .set(TEAM.MEMBER_COUNT, DSL.field(TEAM.MEMBER_COUNT).add(1))
          .where(TEAM.ID.eq(teamId));
      MembershipRecord createdRecord = DSL.using(cfg)
          .insertInto(MEMBERSHIP, MEMBERSHIP.START, MEMBERSHIP.ID_TEAM, MEMBERSHIP.ID_USER)
          .values(Timestamp.from(Instant.now()), teamId, userId)
          .returning(MEMBERSHIP.fields())
          .fetchOne();
      future.complete(createdRecord.into(Membership.class));
    });
    return future;
  }

  @Override
  public Future<Void> leaveTeam(Integer userId) {
    CompletableFuture<Void> future = new CompletableFuture<>();
    DSLWrapper.transaction(dsl, future, cfg -> {
      if (!doesUserHasTeam(userId, cfg, dsl)) {
        throw new IllegalStateException("User is not a member of the team.");
      }
      Integer idTeam = DSL.using(cfg)
          .update(MEMBERSHIP)
          .set(MEMBERSHIP.ISPRESENT, false)
          .set(MEMBERSHIP.END, Timestamp.from(Instant.now()))
          .where(MEMBERSHIP.ID_USER.eq(userId))
          .returning(MEMBERSHIP.ID_TEAM)
          .fetchOne().getId();
      DSL.using(cfg)
          .update(TEAM)
          .set(TEAM.MEMBER_COUNT, DSL.field(TEAM.MEMBER_COUNT).add(-1))
          .where(TEAM.ID.eq(idTeam));
      future.complete(null);
    });
    return future;
  }

  static boolean doesUserHasTeam(Integer userId, Configuration cfg, DSLContext dsl) {
    return DSL.using(cfg)
        .fetchExists(dsl.selectOne()
            .from(MEMBERSHIP)
            .where(MEMBERSHIP.ID_USER.eq(userId))
            .and(MEMBERSHIP.ISPRESENT));
  }
}
