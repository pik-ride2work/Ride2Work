package com.pik.backend.services;

import com.pik.backend.util.RestInputValidator;
import com.pik.backend.util.Validated;
import com.pik.ride2work.Tables;
import com.pik.ride2work.tables.pojos.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;

import static com.pik.ride2work.Tables.USER;

public class GenericService<T> {
    private final Table table;
    private final DSLContext dsl;
    private final RestInputValidator<T> validator;

    public GenericService(Table table, DSLContext dsl, RestInputValidator<T> validator) {
        this.table = table;
        this.dsl = dsl;
        this.validator = validator;
    }

    T update(T input){
        Validated validation = validator.validCreateInput(input);
        if (!validation.isValid()) {
            throw new IllegalArgumentException(validation.getCause());
        }
        return dsl.insertInto(table.asTable())
                .set(dsl.newRecord(table, input))
                .returning(table.fields())
                .fetchOne()
                .into(table.getRecordType().getClass());
    }

    T create(T input);

    void delete(Integer id);
}
