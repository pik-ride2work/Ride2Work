package com.pik.backend.services.jooq_fields;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class GeographyFromTextField extends CustomField<String> {
    private final Field<String> arg0;

    public GeographyFromTextField(Field<String> arg0) {
        super("geography_from_text", SQLDataType.VARCHAR);
        this.arg0 = arg0;
    }

    private QueryPart delegate(Configuration configuration) {
        return DSL.field("ST_GeographyFromText('{0}')", String.class, arg0);
    }

    @Override
    public void accept(Context<?> context) {
        context.visit(delegate(context.configuration()));
    }
}
