package com.pik.backend.services;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

class Crypt extends CustomField<String> {

  static final String GEN_SALT = "gen_salt('bf')";
  private final String arg0;
  private final Field<String> arg1;

  Crypt(String arg0, Field<String> arg1) {
    super("password", SQLDataType.VARCHAR);
    this.arg0 = arg0;
    this.arg1 = arg1;
  }

  private QueryPart delegate(Configuration configuration) {
    return DSL.field(String.format("crypt('%s', {0})", arg0), String.class, arg1);
  }

  @Override
  public void accept(Context<?> context) {
    context.visit(delegate(context.configuration()));
  }
}

