package com.pik.backend.util;

public class Validated {

  private final boolean isValid;
  private final IllegalArgumentException cause;

  private Validated(boolean isValid, String cause) {
    this.isValid = isValid;
    this.cause = new IllegalArgumentException(cause);
  }

  public static Validated invalid(String cause) {
    return new Validated(false, cause);
  }

  public static Validated valid() {
    return new Validated(true, null);
  }

  public boolean isValid() {
    return isValid;
  }

  public IllegalArgumentException getCause() {
    return cause;
  }
}
