package com.reactdevops.timetracker.backend.service.exceptions;

/**
 * Exception for case if user exists
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class ObjectExistsException extends CustomWebException {
  public ObjectExistsException(String message) {
    super(message);
  }

  public ObjectExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
