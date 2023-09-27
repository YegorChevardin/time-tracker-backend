package com.reactdevops.timetracker.backend.service.exceptions;

/**
 * Exception for handling of proceeding web errors created in order to easy handle them in
 * controller
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class CustomWebException extends RuntimeException {
  public CustomWebException(String message) {
    super(message);
  }

  public CustomWebException(String message, Throwable cause) {
    super(message, cause);
  }
}
