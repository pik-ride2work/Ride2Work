package com.pik.backend.services;

import com.pik.backend.util.RestInputValidator;
import com.pik.backend.util.Validated;
import com.pik.ride2work.tables.pojos.User;
import org.jooq.*;

import static com.pik.ride2work.Tables.USER;

public class GenericService<T extends Record> {
    private final Table<T> table;
    private final Class<T> clazz;
    private final Field<Integer> idField;
    private final DSLContext dsl;
    private final RestInputValidator<T> validator;

    public GenericService(Table<T> table, Class<T> clazz, DSLContext dsl, RestInputValidator<T> validator) {
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
        T updatedRecord = dsl.update(table)
                .set(dsl.newRecord(table, input))
                .where(idField.eq(id))
                .returning(table.fields())
                .fetchOne();
        return (updatedRecord == null) ? null : updatedRecord.into(clazz);
    }

    void delete(Integer id) {
        dsl.delete(table)
                .where(idField.eq(id));
    }
}
