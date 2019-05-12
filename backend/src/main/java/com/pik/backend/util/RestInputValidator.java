package com.pik.backend.util;

public interface RestInputValidator<T> {
    Validated validateCreateInput(T input);

    Validated validateUpdateInput(T input);
}
