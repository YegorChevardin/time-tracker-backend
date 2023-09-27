package com.reactdevops.timetracker.backend.service.exceptions;

/**
 * Exception for case if object was not found
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class ObjectNotFoundException extends CustomWebException {
  public ObjectNotFoundException(String message) {
    super(message);
  }

  public ObjectNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
