package com.pik.backend;

import com.google.common.base.Strings;

public class StringValidator {

    private StringValidator() {
    }

    public static StringValidator getInstance() {
        return new StringValidator();
    }

    boolean lettersOnly(int min, int max, String input) {
        if (!entryCheck(min, max, input)) {
            return false;
        }
        int length = input.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    boolean lettersAndDigits(int min, int max, String input) {
        if (!entryCheck(min, max, input)) {
            return false;
        }
        int length = input.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean entryCheck(int min, int max, String input) {
        if (Strings.isNullOrEmpty(input)) {
            return false;
        }
        if (max < min || min < 0) {
            throw new IllegalArgumentException("Min or max value is invalid.");
        }
        int length = input.length();
        return length >= min && length <= max;
    }

}
