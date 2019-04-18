package com.pik.backend;

import com.google.common.base.Strings;
import com.pik.ride2work.tables.pojos.User;
import org.apache.commons.validator.routines.EmailValidator;

public class UserInputValidator implements RestInputValidator<User> {

    private static EmailValidator emailValidator = EmailValidator.getInstance();
    private static StringValidator stringValidator = StringValidator.getInstance();

    @Override
    public Validated validCreateInput(User input) {
        if (input.getId() != null) {
            return Validated.invalid("Created user should not have ID!");
        }
        if (!emailValidator.isValid(input.getEmail())) {
            return Validated.invalid("Invalid email format.");
        }
        if (!stringValidator.lettersOnly(6, 32, input.getUsername())) {
            return Validated.invalid("Invalid username format (Should be 6-32 characters long)");
        }
        if (!stringValidator.lettersOnly(8, 32, input.getPassword())) {
            return Validated.invalid("Invalid password format.(Should be 8-32 characters long)");
        }
        if (!stringValidator.lettersOnly(2, 32, input.getFirstName())) {
            return Validated.invalid("Invalid firstname format.");
        }
        if (!stringValidator.lettersOnly(2, 32, input.getLastName())) {
            return Validated.invalid("Invalid firstname format.");
        }
        return Validated.valid();
    }

    @Override
    public Validated validUpdateInput(User input) {
        if (input.getId() == null) {
            return Validated.invalid("Updated must have ID!");
        }
        if (input.getEmail() != null && !emailValidator.isValid(input.getEmail())) {
            return Validated.invalid("Invalid email format.");
        }
        if (Strings.isNullOrEmpty(input.getUsername()) && !stringValidator.lettersOnly(6, 32, input.getUsername())) {
            return Validated.invalid("Invalid username format (Should be 6-32 characters long)");
        }
        if (Strings.isNullOrEmpty(input.getPassword()) && !stringValidator.lettersOnly(8, 32, input.getPassword())) {
            return Validated.invalid("Invalid password format.(Should be 8-32 characters long)");
        }
        if (Strings.isNullOrEmpty(input.getFirstName()) && !stringValidator.lettersOnly(2, 32, input.getFirstName())) {
            return Validated.invalid("Invalid firstname format.");
        }
        if (Strings.isNullOrEmpty(input.getLastName()) && !stringValidator.lettersOnly(2, 32, input.getLastName())) {
            return Validated.invalid("Invalid firstname format.");
        }
        return Validated.valid();
    }

}
