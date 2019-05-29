package com.pik.backend.services.jooq_fields;

import com.pik.backend.custom_daos.Coordinates;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class PointField extends CustomField<String> {
    private final Coordinates coords;

    public PointField(Coordinates coords) {
        super("point", SQLDataType.VARCHAR);
        this.coords = coords;
    }

    private QueryPart delegate(Configuration configuration) {
        return DSL.field(String.format("ST_GeographyFromText('SRID=4326;POINT(%s %s)')", coords.getLatitude(), coords.getLongitude()));
    }

    @Override
    public void accept(Context<?> context) {
        context.visit(delegate(context.configuration()));
    }
}
