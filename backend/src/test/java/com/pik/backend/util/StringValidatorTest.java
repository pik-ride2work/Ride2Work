package com.pik.backend.util;

import com.pik.backend.util.StringValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringValidatorTest {

    private static StringValidator validator = StringValidator.getInstance();
    private static String ANY_STRING = "abc";
    private static String FOUR_LETTER_STRING = "abcd";
    private static String ELEVEN_LETTER_STRING = "abcdefghijk";
    private static String MIXED_FOUR_LETTER_STRING = "2A3c";
    private static String MIXED_ELEVEN_LETTER_STRING = "ab3Def9h1jk";
    private static String SPECIAL_CHARS = "a!#[]";

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

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnWrongParamsLettersDigits_1() {
        validator.lettersAndDigits(5, 3, ANY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnWrongParamsLettersDigits_2() {
        validator.lettersAndDigits(-1, 0, ANY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnWrongParamsLettersDigits_3() {
        validator.lettersAndDigits(1, -1, ANY_STRING);
    }

    @Test
    public void shouldReturnFalseForNullOrEmptyInputLettersOnly() {
        assertFalse(validator.lettersOnly(1, 10, null));
        assertFalse(validator.lettersOnly(1, 10, ""));
    }

    @Test
    public void shouldReturnFalseForNullOrEmptyInputLettersDigits() {
        assertFalse(validator.lettersAndDigits(1, 10, ""));
        assertFalse(validator.lettersAndDigits(1, 10, null));
    }

    @Test
    public void shouldReturnFalseForInvalidInputLetters() {
        assertFalse(validator.lettersOnly(1, 3, FOUR_LETTER_STRING));
        assertFalse(validator.lettersOnly(3, 3, FOUR_LETTER_STRING));
        assertFalse(validator.lettersOnly(12, Integer.MAX_VALUE, ELEVEN_LETTER_STRING));
        assertFalse(validator.lettersOnly(2, Integer.MAX_VALUE, "a"));
        assertFalse(validator.lettersOnly(2, 10, MIXED_ELEVEN_LETTER_STRING));
        assertFalse(validator.lettersOnly(2, 10, SPECIAL_CHARS));
    }

    @Test
    public void shouldReturnTrueForValidInputLetters() {
        assertTrue(validator.lettersOnly(0, Integer.MAX_VALUE, ELEVEN_LETTER_STRING));
        assertTrue(validator.lettersOnly(4, 4, FOUR_LETTER_STRING));
        assertTrue(validator.lettersOnly(6, 12, ELEVEN_LETTER_STRING));
        assertTrue(validator.lettersOnly(2, 11, ELEVEN_LETTER_STRING));
        assertTrue(validator.lettersOnly(11, 13, ELEVEN_LETTER_STRING));
    }

    @Test
    public void shouldReturnFalseForInvalidInputLettersDigits() {
        assertFalse(validator.lettersAndDigits(1, 3, FOUR_LETTER_STRING));
        assertFalse(validator.lettersAndDigits(5, 8, FOUR_LETTER_STRING));
        assertFalse(validator.lettersAndDigits(10, 10, ELEVEN_LETTER_STRING));
        assertFalse(validator.lettersAndDigits(10, 10, MIXED_ELEVEN_LETTER_STRING));
        assertFalse(validator.lettersAndDigits(5, Integer.MAX_VALUE, MIXED_FOUR_LETTER_STRING));
        assertFalse(validator.lettersAndDigits(0, Integer.MAX_VALUE, SPECIAL_CHARS));
    }

    @Test
    public void shouldReturnTrueForValidInputLettersDigits(){
        assertTrue(validator.lettersAndDigits(1, 4, FOUR_LETTER_STRING));
        assertTrue(validator.lettersAndDigits(1, 4, MIXED_FOUR_LETTER_STRING));
        assertTrue(validator.lettersAndDigits(4, 8, MIXED_FOUR_LETTER_STRING));
        assertTrue(validator.lettersAndDigits(11, 11, MIXED_ELEVEN_LETTER_STRING));
        assertTrue(validator.lettersAndDigits(9, 11, ELEVEN_LETTER_STRING));
    }
}