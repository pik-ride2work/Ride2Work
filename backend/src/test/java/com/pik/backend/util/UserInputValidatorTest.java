package com.pik.backend.util;

import com.pik.ride2work.tables.pojos.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserInputValidatorTest {

    private static UserInputValidator validator = new UserInputValidator();

    @Test
    public void shouldReturnTrueForValidUserInputDuringRegistration() {
        User user = new User(null, "johnny", "Password",
                "John", "Smith", "john@smith.com" );
        assertTrue(validator.validateRegistrationInput(user).isValid());
    }

    @Test
    public void shouldReturnFalseForProvidingIdDuringRegistration() {
        User user = new User(1, "johnny", "Password",
                "John", "Smith", "john@smith.com");
        assertFalse(validator.validateRegistrationInput(user).isValid());
    }

    @Test
    public void shouldReturnForInvalidEmailDuringRegistration() {
        User user = new User(null, "johnny", "Password",
                "John", "Smith", "johnasmith.com");
        assertFalse(validator.validateRegistrationInput(user).isValid());
    }

    @Test
    public void shouldReturnFalseForInvalidLastNameDuringRegistration() {
        User user = new User(null, "johnny", "Password",
                "John", "1mith", "john@smith.com");
        assertFalse(validator.validateRegistrationInput(user).isValid());
    }

    @Test
    public void shouldReturnFalseForInvalidFirstNameDuringRegistration() {
        User user = new User(null, "johnny", "Password",
                "Jo2n", "Smith", "john@smith.com");
        assertFalse(validator.validateRegistrationInput(user).isValid());
    }

    @Test
    public void shouldReturnTrueForValidUserInputDuringProfileUpdate() {
        User user = new User(1, "johnny", "Password",
                "John", "Smith", "john@smith.com" );
        assertTrue(validator.validateUpdateInput(user).isValid());
    }

    @Test
    public void shouldReturnFalseForNullIdDuringProfileUpdate() {
        User user = new User(null, "johnny", "Password",
                "John", "Smith", "john@smith.com");
        assertFalse(validator.validateUpdateInput(user).isValid());
    }

    @Test
    public void shouldReturnForInvalidEmailDuringProfileUpdate() {
        User user = new User(null, "johnny", "Password",
                "John", "Smith", "johnasmith.com");
        assertFalse(validator.validateUpdateInput(user).isValid());
    }

    @Test
    public void shouldReturnFalseForInvalidLastNameDuringProfileUpdate() {
        User user = new User(null, "johnny", "Password",
                "John", "1mith", "john@smith.com");
        assertFalse(validator.validateUpdateInput(user).isValid());
    }

    @Test
    public void shouldReturnFalseForInvalidFirstNameDuringProfileUpdate() {
        User user = new User(null, "johnny", "Password",
                "John", "1mith", "john@smith.com");
        assertFalse(validator.validateUpdateInput(user).isValid());
    }

}
