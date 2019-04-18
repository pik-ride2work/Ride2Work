package com.pik.backend;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorUtilTest {

    private static ValidatorUtil validator = ValidatorUtil.getInstance();
    private static String ANY_STRING = "abc";
    private static String FOUR_LETTER_STRING = "abcd";
    private static String SEVEN_LETTER_STRING = "abcdefg";
    private static String ELEVEN_LETTER_STRING = "abcdefghijk";

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnWrongParamsLettersOnly_1() {
        validator.lettersOnly(5, 3, ANY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnWrongParamsLettersOnly_2() {
        validator.lettersOnly(-1, 0, ANY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnWrongParamsLettersOnly_3() {
        validator.lettersOnly(1, -1, ANY_STRING);
    }

    @Test
    public void shouldReturnFalseForNullOrEmptyInputLettersOnly() {
        assertFalse(validator.lettersOnly(1, 10, null));
        assertFalse(validator.lettersOnly(1, 10, ""));
    }

    @Test
    public void shouldReturnFalseForInvalidInput() {
        assertFalse(validator.lettersOnly(1, 3, FOUR_LETTER_STRING));
        assertFalse(validator.lettersOnly(3, 3, FOUR_LETTER_STRING));
        assertFalse(validator.lettersOnly(8, Integer.MAX_VALUE, SEVEN_LETTER_STRING));
        assertFalse(validator.lettersOnly(2, Integer.MAX_VALUE, "a"));
    }

    @Test
    public void shouldReturnTrueForValidInput() {
        assertTrue(validator.lettersOnly(0, Integer.MAX_VALUE, ELEVEN_LETTER_STRING));
        assertTrue(validator.lettersOnly(4, 4, FOUR_LETTER_STRING));
        assertTrue(validator.lettersOnly(6, 9, SEVEN_LETTER_STRING));
        assertTrue(validator.lettersOnly(2, 11, ELEVEN_LETTER_STRING));
        assertTrue(validator.lettersOnly(11, 13, ELEVEN_LETTER_STRING));
    }
}