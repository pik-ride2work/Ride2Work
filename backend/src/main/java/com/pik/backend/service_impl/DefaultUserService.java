package com.pik.backend.service_impl;

import com.pik.backend.UserService;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.commons.validator.routines.EmailValidator;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.pik.ride2work.Tables.*;

@Repository
public class DefaultUserService implements UserService {
    private final DSLContext dsl;

    @Autowired
    public DefaultUserService(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public User create(User user) {
        return dsl.insertInto(USER)
                .set(dsl.newRecord(USER, user))
                .returning(USER.fields())
                .fetchOne()
                .into(User.class);
    }

    @Override
    public User update(User user, Integer id) {
        return dsl.update(USER)
                .set(dsl.newRecord(USER, user))
                .returning(USER.fields())
                .fetchOne()
                .into(User.class);
    }

    @Override
    public User getByUsername(String username) {
        UserRecord userRecord = dsl.selectFrom(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOne();
        return (userRecord == null) ? null : userRecord.into(User.class);
    }

}


