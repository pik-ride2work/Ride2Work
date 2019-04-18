package com.pik.backend;

public interface RestInputValidator<T> {
    Validated validCreateInput(T input);

    Validated validUpdateInput(T input);
}
