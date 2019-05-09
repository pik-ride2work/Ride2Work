package com.pik.backend.services;

import com.pik.backend.util.DSLWrapper;
import com.pik.backend.util.UserInputValidator;
import com.pik.backend.util.Validated;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static com.pik.ride2work.Tables.USER;

@Repository
public class DefaultUserService implements UserService {

    private final UserInputValidator validator = new UserInputValidator();
    private final DSLContext dsl;

    @Autowired
    public DefaultUserService(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Future<User> create(User user) {
        CompletableFuture<User> future = new CompletableFuture<>();
        Validated validation = this.validator.validateCreateInput(user);
        if (!validation.isValid()) {
            future.completeExceptionally(validation.getCause());
            return future;
        }
        DSLWrapper.transaction(dsl, future, cfg -> {
            User created = dsl.insertInto(USER)
                    .set(dsl.newRecord(USER, user))
                    .returning(USER.fields())
                    .fetchOne()
                    .into(User.class);
            future.complete(created);
        });
        return future;
    }

    @Override
    public Future<User> update(User user) {
        CompletableFuture<User> future = new CompletableFuture<>();
        Validated validation = this.validator.validateUpdateInput(user);
        if (!validation.isValid()) {
            future.completeExceptionally(validation.getCause());
            return future;
        }
        DSLWrapper.transaction(dsl, future, cfg -> {
            UserRecord updatedRecord = dsl.update(USER)
                    .set(dsl.newRecord(USER, user))
                    .where(USER.ID.eq(user.getId()))
                    .returning(USER.fields())
                    .fetchOne();
            if (updatedRecord == null) {
                future.completeExceptionally(new NotFoundException("User not Found"));
                return;
            }
            future.complete(updatedRecord.into(User.class));
        });
        return future;
    }

    @Override
    public Future<User> getByUsername(String username) {
        CompletableFuture<User> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            UserRecord userRecord = dsl.selectFrom(USER)
                    .where(USER.USERNAME.eq(username))
                    .fetchOne();
            if (userRecord == null) {
                future.completeExceptionally(new NotFoundException("User not Found"));
                return;
            }
            future.complete(userRecord.into(User.class));
        });
        return future;
    }

}


