package com.pik.backend;

public class Validated {
    private final boolean isValid;
    private final RuntimeException cause;

    public Validated(boolean isValid, String cause) {
        this.isValid = isValid;
        this.cause = new RuntimeException(cause);
    }

    public boolean isValid() {
        return isValid;
    }

    public RuntimeException getCause() {
        return cause;
    }
}
