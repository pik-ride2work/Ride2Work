package com.pik.backend.services.jooq_fields;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class CryptField extends CustomField<String> {

    static final String GEN_SALT = "gen_salt('bf')";
    private static final String CRYPT_FIELD_TEMPLATE = "crypt('%s', %s)";
    private final String pwd;
    private Field<String> pwdField;

    public CryptField(String pwd) {
        super("password", SQLDataType.VARCHAR);
        this.pwd = pwd;
    }

    public CryptField(String pwd, Field<String> field){
        this(pwd);
        this.pwdField = field;
    }

    private QueryPart delegate(Configuration configuration) {
        if(pwdField != null){
            //login
            return DSL.field(String.format(CRYPT_FIELD_TEMPLATE, pwd, pwdField));
        }
        //registration
        return DSL.field(String.format(CRYPT_FIELD_TEMPLATE, pwd, GEN_SALT));
    }

    @Override
    public void accept(Context<?> context) {
        context.visit(delegate(context.configuration()));
    }
}

