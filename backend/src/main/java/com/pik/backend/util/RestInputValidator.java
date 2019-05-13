package com.pik.backend.util;

import com.pik.ride2work.tables.pojos.User;

public interface RestInputValidator<T> {
    Validated validateRegistrationInput(T input);

    Validated validateUpdateInput(T input);

}
