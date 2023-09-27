package com.reactdevops.timetracker.backend.web.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for helping create errors
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public final class ErrorCreatingHelper {
  private ErrorCreatingHelper() {}

  /** Method for creating map with specified error message for better json object */
  public static Map<String, String> createError(String errorMessage) {
    Map<String, String> error = new HashMap<>();
    error.put("Error", errorMessage);
    return error;
  }
}
