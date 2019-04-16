package com.pik.backend.service_impl;

import com.pik.backend.UserService;
import com.pik.ride2work.Tables;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pik.ride2work.Tables.*;

@Repository
public class DefaultUserService implements UserService {
    @Autowired
    DSLContext dsl;

    @Override
    public void create(Long id, String username, String password) {
        dsl.insertInto(USER)
                .set(USER.ID, id)
                .set(USER.USERNAME, username)
                .set(USER.PASSWORD, password)
                .execute();
    }

    @Override
    public User getByUsername(String username) {
        Result<UserRecord> records = dsl.selectFrom(USER)
                .where(USER.USERNAME.eq(username))
                .fetch();
        List<User> result = records.into(User.class);
        return (result.isEmpty()) ? null : result.get(0);
    }

}
