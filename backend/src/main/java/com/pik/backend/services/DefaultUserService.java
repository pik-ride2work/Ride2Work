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
    private final GenericService<User, UserRecord> genericService;
    private final UserInputValidator validator;
    private final DSLContext dsl;

    @Autowired
    public DefaultUserService(GenericService<User, UserRecord> genericService, UserInputValidator validator, DSLContext dsl) {
        this.genericService = genericService;
        this.validator = validator;
        this.dsl = dsl;
    }

    @Override
    public User create(User user) {
        return genericService.create(user);
    }

    @Override
    public User update(User user, Integer id) {
        return genericService.update(user, id);
    }

    @Override
    public User getByUsername(String username) {
        UserRecord userRecord = dsl.selectFrom(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOne();
        return (userRecord == null) ? null : userRecord.into(User.class);
    }

}


