package com.pik.backend.services;

import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

class Crypt extends CustomField<String> {

  final Field<String> arg0;

  Crypt(Field<String> arg0) {
    super("to_char", SQLDataType.VARCHAR);
    this.arg0 = arg0;
  }

  private QueryPart delegate(Configuration configuration) {
    return DSL.field("TO_CHAR({0}, {1})", String.class, arg0);
  }

  @Override
  public void accept(Context<?> context) {
    context.visit(delegate(context.configuration()));
  }
}

