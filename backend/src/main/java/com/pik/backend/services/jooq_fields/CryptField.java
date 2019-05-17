package com.pik.backend.services.jooq_fields;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class CryptField extends CustomField<String> {

    static final String GEN_SALT = "gen_salt('bf')";
    private final String arg0;

    public CryptField(String arg0) {
        super("password", SQLDataType.VARCHAR);
        this.arg0 = arg0;
    }

    private QueryPart delegate(Configuration configuration) {
        return DSL.field(String.format("crypt('%s', %s)", arg0, GEN_SALT));
    }

    @Override
    public void accept(Context<?> context) {
        context.visit(delegate(context.configuration()));
    }
}

