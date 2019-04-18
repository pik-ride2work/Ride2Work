package com.pik.backend;

import com.google.common.base.Strings;

public class ValidatorUtil {

    private ValidatorUtil(){
    }

    public static ValidatorUtil getInstance(){
        return new ValidatorUtil();
    }

    boolean lettersOnly(int min, int max, String input){
        if(Strings.isNullOrEmpty(input)){
            return false;
        }
        if(max < min || min < 0){
            throw new IllegalArgumentException("Min or max value is invalid.");
        }
        int length = input.length();
        return length >= min && length <= max;
    }

}
