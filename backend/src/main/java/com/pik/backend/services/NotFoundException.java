package com.pik.backend.services;

public class NotFoundException extends Throwable {

  public NotFoundException(String message) {
    super(message);
  }
}
