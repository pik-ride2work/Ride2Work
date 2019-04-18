package com.pik.backend.service_impl;

import com.pik.backend.UserInputValidator;
import com.pik.backend.UserService;
import com.pik.backend.Validated;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.pik.ride2work.Tables.*;

@Repository
public class DefaultUserService implements UserService {
    private final UserInputValidator validator = new UserInputValidator();
    private final DSLContext dsl;

    @Autowired
    public DefaultUserService(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public User create(User user) {
        Validated validator = this.validator.validCreateInput(user);
        if (!validator.isValid()) {
            throw new IllegalArgumentException(validator.getCause());
        }
        return dsl.insertInto(USER)
                .set(dsl.newRecord(USER, user))
                .returning(USER.fields())
                .fetchOne()
                .into(User.class);
    }

    @Override
    public User update(User user) {
        Validated validator = this.validator.validUpdateInput(user);
        if (!validator.isValid()) {
            throw new IllegalArgumentException(validator.getCause());
        }
        UserRecord updatedRecord= dsl.update(USER)
                .set(dsl.newRecord(USER, user))
                .where(USER.ID.eq(user.getId()))
                .returning(USER.fields())
                .fetchOne();
        return (updatedRecord == null) ? null : updatedRecord.into(User.class);
    }

    @Override
    public User getByUsername(String username) {
        UserRecord userRecord = dsl.selectFrom(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOne();
        return (userRecord == null) ? null : userRecord.into(User.class);
    }

}


