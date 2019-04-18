package com.pik.backend;

import com.google.common.base.Strings;
import com.pik.ride2work.tables.pojos.User;
import org.apache.commons.validator.routines.EmailValidator;

public class UserInputValidator implements RestInputValidator<User> {

    private static EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public Validated validCreateInput(User input) {
        emailValidator.isValid(input.getEmail());
        return null;
    }

    @Override
    public Validated validUpdateInput(User input) {
        return null;
    }

    @Override
    public Validated validGetInput(User input) {
        return null;
    }

}
