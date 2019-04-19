package com.pik.backend.services;

import com.pik.backend.util.RestInputValidator;
import com.pik.backend.util.Validated;
import org.jooq.*;

public class GenericService<T, R extends Record> {
    private final Table<R> table;
    private final Class<T> clazz;
    private final Field<Integer> idField;
    private final DSLContext dsl;
    private final RestInputValidator<T> validator;

    public GenericService(Table<R> table, Class<T> clazz, DSLContext dsl, RestInputValidator<T> validator) {
        this.clazz = clazz;
        this.table = table;
        this.idField = table.field("id", Integer.class);
        this.dsl = dsl;
        this.validator = validator;
    }

    T create(T input) {
        Validated validation = validator.validCreateInput(input);
        if (!validation.isValid()) {
            throw new IllegalArgumentException(validation.getCause());
        }
        return dsl.insertInto(table)
                .set(dsl.newRecord(table, input))
                .returning(table.fields())
                .fetchOne()
                .into(clazz);
    }

    T update(T input, Integer id) {
        Validated validation = validator.validUpdateInput(input);
        if (!validation.isValid()) {
            throw new IllegalArgumentException(validation.getCause());
        }
        R updatedRecord = dsl.update(table)
                .set(dsl.newRecord(table, input))
                .where(idField.eq(id))
                .returning(table.fields())
                .fetchOne();
        return (updatedRecord == null) ? null : updatedRecord.into(clazz);
    }

    int delete(Integer id) {
        return dsl.delete(table)
                .where(idField.eq(id))
                .execute();
    }
}
