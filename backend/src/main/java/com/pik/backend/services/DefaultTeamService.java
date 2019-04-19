package com.pik.backend.services;

import com.pik.backend.util.TeamInputValidator;
import com.pik.backend.util.Validated;
import com.pik.ride2work.tables.pojos.Team;
import com.pik.ride2work.tables.records.TeamRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pik.ride2work.Tables.*;

@Repository
public class DefaultTeamService implements TeamService {
    private final TeamInputValidator validator;
    private final DSLContext dsl;

    public DefaultTeamService(TeamInputValidator validator, DSLContext dsl) {
        this.validator = validator;
        this.dsl = dsl;
    }

    @Override
    public Team create(Team team) {
        Validated validation = validator.validCreateInput(team);
        if (!validation.isValid()) {
            throw new IllegalArgumentException(validation.getCause());
        }
        return dsl.insertInto(TEAM)
                .set(dsl.newRecord(TEAM, team))
                .returning(TEAM.fields())
                .fetchOne()
                .into(Team.class);
    }

    @Override
    public Team update(Team team) {
        Validated validation = validator.validUpdateInput(team);
        if (validation.isValid()) {
            throw new IllegalArgumentException(validation.getCause());
        }
        TeamRecord updatedRecord = dsl.update(TEAM)
                .set(dsl.newRecord(TEAM, team))
                .where(TEAM.ID.eq(team.getId()))
                .returning(TEAM.fields())
                .fetchOne();
        return (updatedRecord == null) ? null : updatedRecord.into(Team.class);
    }


    @Override
    public void delete(Integer id) {
        dsl.delete(TEAM)
                .where(TEAM.ID.eq(id));
    }

    @Override
    public List<Team> list() {
        return dsl.selectFrom(TEAM)
                .fetch()
                .into(Team.class);
    }

    @Override
    public Team getByName(String name) {
        TeamRecord teamRecord = dsl.selectFrom(TEAM)
                .where(TEAM.NAME.eq(name))
                .fetchOne();
        return (teamRecord == null) ? null : teamRecord.into(Team.class);
    }
}
