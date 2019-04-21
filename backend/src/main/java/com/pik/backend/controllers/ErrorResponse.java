package com.pik.backend.controllers;

import org.json.JSONObject;

public class ErrorResponse {

  public static String error(Throwable e) {
    return new JSONObject()
        .put("message", e.getMessage()).toString();
  }

}
