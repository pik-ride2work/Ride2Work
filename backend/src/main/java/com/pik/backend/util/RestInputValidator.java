package com.pik.backend.util;

import com.pik.backend.util.Validated;

public interface RestInputValidator<T> {
    Validated validateCreateInput(T input);

    Validated validateUpdateInput(T input);
}
