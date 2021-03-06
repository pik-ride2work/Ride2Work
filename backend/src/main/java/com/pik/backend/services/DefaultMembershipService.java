package com.pik.backend.services;

import static com.pik.ride2work.Tables.MEMBERSHIP;
import static com.pik.ride2work.Tables.TEAM;

import com.pik.backend.util.DSLWrapper;
import com.pik.ride2work.tables.pojos.Membership;
import com.pik.ride2work.tables.records.MembershipRecord;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultMembershipService implements MembershipService {

    private final DSLContext dsl;

    public DefaultMembershipService(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Future<Void> changeOwner(Integer userId, Integer teamId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            boolean isUserInTheTeam = DSL.using(cfg)
                    .fetchExists(dsl.selectOne()
                            .from(MEMBERSHIP)
                            .where(MEMBERSHIP.ID_USER.eq(userId))
                            .and(MEMBERSHIP.ID_TEAM.eq(teamId)));
            if (!isUserInTheTeam) {
                future.completeExceptionally(new IllegalStateException("User is not the part of the team."));
                return;
            }
            DSL.using(cfg)
                    .update(MEMBERSHIP)
                    .set(MEMBERSHIP.IS_OWNER, false)
                    .where(MEMBERSHIP.IS_OWNER)
                    .and(MEMBERSHIP.IS_PRESENT)
                    .execute();
            DSL.using(cfg)
                    .update(MEMBERSHIP)
                    .set(MEMBERSHIP.IS_OWNER, true)
                    .where(MEMBERSHIP.ID_USER.eq(userId))
                    .and(MEMBERSHIP.IS_PRESENT)
                    .execute();
            future.complete(null);
        });
        return future;
    }

    @Override
    public Future<List<Membership>> getMembers(Integer teamId) {
        CompletableFuture<List<Membership>> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            Result<MembershipRecord> records = DSL.using(cfg)
                    .selectFrom(MEMBERSHIP)
                    .where(MEMBERSHIP.IS_PRESENT)
                    .and(MEMBERSHIP.ID_TEAM.eq(teamId))
                    .fetch();
            if (records == null) {
                future.completeExceptionally(new NotFoundException("No members found"));
                return;
            }
            List<Membership> members = records.into(Membership.class);
            future.complete(members);
        });
        return future;
    }

    @Override
    public Future<Membership> joinTeam(Integer userId, Integer teamId) {
        CompletableFuture<Membership> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            if (doesUserHasTeam(userId, cfg, dsl)) {
                future.completeExceptionally(new IllegalStateException("User is a member of a team already"));
                return;
            }
            DSL.using(cfg)
                    .update(TEAM)
                    .set(TEAM.MEMBER_COUNT, TEAM.MEMBER_COUNT.add(1))
                    .where(TEAM.ID.eq(teamId))
                    .execute();
            MembershipRecord createdRecord = DSL.using(cfg)
                    .insertInto(MEMBERSHIP, MEMBERSHIP.START, MEMBERSHIP.ID_TEAM, MEMBERSHIP.ID_USER, MEMBERSHIP.IS_OWNER)
                    .values(Timestamp.from(Instant.now()), teamId, userId, false)
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
                future.completeExceptionally(new IllegalStateException("User is not a member of the team."));
                return;
            }
            Integer idTeam = DSL.using(cfg)
                    .update(MEMBERSHIP)
                    .set(MEMBERSHIP.IS_PRESENT, false)
                    .set(MEMBERSHIP.END, Timestamp.from(Instant.now()))
                    .where(MEMBERSHIP.ID_USER.eq(userId))
                    .and(MEMBERSHIP.IS_PRESENT.eq(true))
                    .returning(MEMBERSHIP.ID_TEAM)
                    .fetchOne()
                    .getValue(MEMBERSHIP.ID_TEAM);
            if (idTeam == null) {
                future.completeExceptionally(new NotFoundException("User not found"));
                return;
            }
            DSL.using(cfg)
                    .update(TEAM)
                    .set(TEAM.MEMBER_COUNT, TEAM.MEMBER_COUNT.subtract(1))
                    .where(TEAM.ID.eq(idTeam))
                    .execute();
            future.complete(null);
        });
        return future;
    }

    @Override
    public Future<Membership> getCurrentMembershipByUserId(Integer userId) {
        CompletableFuture<Membership> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            MembershipRecord membershipRecord = getCurrentMembership(userId, cfg);
            if (membershipRecord == null) {
                future.completeExceptionally(new NotFoundException("Membership or user not found."));
                return;
            }
            future.complete(membershipRecord.into(Membership.class));
        });
        return future;
    }

    static boolean doesUserHasTeam(Integer userId, Configuration cfg, DSLContext dsl) {
        return DSL.using(cfg)
                .fetchExists(dsl.selectOne()
                        .from(MEMBERSHIP)
                        .where(MEMBERSHIP.ID_USER.eq(userId))
                        .and(MEMBERSHIP.IS_PRESENT));
    }

    static MembershipRecord getCurrentMembership(Integer userId, Configuration cfg){
        return DSL.using(cfg)
            .selectFrom(MEMBERSHIP)
            .where(MEMBERSHIP.IS_PRESENT)
            .and(MEMBERSHIP.ID_USER.eq(userId))
            .fetchOne();
    }

}
