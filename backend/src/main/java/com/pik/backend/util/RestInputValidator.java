package com.pik.backend.util;

import com.pik.backend.util.Validated;

public interface RestInputValidator<T> {
    Validated validCreateInput(T input);

    Validated validUpdateInput(T input);
}
