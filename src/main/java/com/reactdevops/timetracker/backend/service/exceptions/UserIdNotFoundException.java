package com.reactdevops.timetracker.backend.service.exceptions;

/**
 * Exception for case if user id was not found
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class UserIdNotFoundException extends CustomWebException {
  public UserIdNotFoundException(String message) {
    super(message);
  }

  public UserIdNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
