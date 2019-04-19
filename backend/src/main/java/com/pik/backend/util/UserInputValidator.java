package com.pik.backend.util;

import com.google.common.base.Strings;
import com.pik.ride2work.tables.pojos.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import static java.lang.String.*;

@Component
public class UserInputValidator implements RestInputValidator<User> {

    private static EmailValidator emailValidator = EmailValidator.getInstance();
    private static StringValidator stringValidator = StringValidator.getInstance();
    private static String USERNAME_FORMAT_TEMPLATE = "Invalid username format (Should be %s-%s characters long, no special chars)";
    private static String PASSWORD_FORMAT_TEMPLATE = "Invalid password format (Should be %s-%s characters long)";
    private static String NAME_FORMAT_TEMPLATE = "Invalid %s format (Should be %s-%s characters long, English letters only)";
    private static int CRED_MIN_LEN = 6;
    private static int CRED_MAX_LEN = 32;
    private static int NAME_MIN_LEN = 2;
    private static int NAME_MAX_LEN = 32;

    @Override
    public Validated validCreateInput(User input) {
        if (input.getId() != null) {
            return Validated.invalid("Created user should not have ID!");
        }
        if (!emailValidator.isValid(input.getEmail())) {
            return Validated.invalid("Invalid email format.");
        }
        if (!stringValidator.lettersAndDigits(CRED_MIN_LEN, CRED_MAX_LEN, input.getUsername())) {
            return Validated.invalid(format(USERNAME_FORMAT_TEMPLATE, CRED_MIN_LEN, CRED_MAX_LEN));
        }
        if (!stringValidator.anyChars(CRED_MIN_LEN, CRED_MAX_LEN, input.getPassword())) {
            return Validated.invalid(format(PASSWORD_FORMAT_TEMPLATE, CRED_MIN_LEN, CRED_MAX_LEN));
        }
        if (!stringValidator.lettersOnly(NAME_MIN_LEN, NAME_MAX_LEN, input.getFirstName())) {
            return Validated.invalid(format(NAME_FORMAT_TEMPLATE, "first name", NAME_MIN_LEN, NAME_MAX_LEN));
        }
        if (!stringValidator.lettersOnly(NAME_MIN_LEN, NAME_MAX_LEN, input.getLastName())) {
            return Validated.invalid(format(NAME_FORMAT_TEMPLATE, "last name", NAME_MIN_LEN, NAME_MAX_LEN));
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
        if (Strings.isNullOrEmpty(input.getUsername()) && !stringValidator.lettersAndDigits(CRED_MIN_LEN, CRED_MAX_LEN, input.getUsername())) {
            return Validated.invalid(format(USERNAME_FORMAT_TEMPLATE, CRED_MIN_LEN, CRED_MAX_LEN));
        }
        if (Strings.isNullOrEmpty(input.getPassword()) && !stringValidator.anyChars(CRED_MIN_LEN, CRED_MAX_LEN, input.getPassword())) {
            return Validated.invalid(format(PASSWORD_FORMAT_TEMPLATE, CRED_MIN_LEN, CRED_MAX_LEN));
        }
        if (Strings.isNullOrEmpty(input.getFirstName()) && !stringValidator.lettersOnly(NAME_MIN_LEN, NAME_MAX_LEN, input.getFirstName())) {
            return Validated.invalid(format(NAME_FORMAT_TEMPLATE, "first name", NAME_MIN_LEN, NAME_MAX_LEN));
        }
        if (Strings.isNullOrEmpty(input.getLastName()) && !stringValidator.lettersOnly(NAME_MIN_LEN, NAME_MAX_LEN, input.getLastName())) {
            return Validated.invalid(format(NAME_FORMAT_TEMPLATE, "last name", NAME_MIN_LEN, NAME_MAX_LEN));
        }
        return Validated.valid();
    }

}
