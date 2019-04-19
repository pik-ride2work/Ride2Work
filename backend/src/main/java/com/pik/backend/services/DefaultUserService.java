package com.pik.backend.services;

import com.pik.backend.util.UserInputValidator;
import com.pik.backend.util.Validated;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.pik.ride2work.Tables.*;

@Repository
public class DefaultUserService implements UserService {
    private final UserInputValidator validator;
    private final DSLContext dsl;

    @Autowired
    public DefaultUserService(UserInputValidator validator, DSLContext dsl) {
        this.validator = validator;
        this.dsl = dsl;
    }

    @Override
    public User getByUsername(String username) {
        UserRecord userRecord = dsl.selectFrom(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOne();
        return (userRecord == null) ? null : userRecord.into(User.class);
    }

}


