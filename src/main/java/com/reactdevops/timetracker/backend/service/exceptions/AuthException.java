package com.reactdevops.timetracker.backend.service.exceptions;

/**
 * Exception in case of auth failure
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class AuthException extends CustomWebException {
  public AuthException(String message) {
    super(message);
  }

  public AuthException(String message, Throwable cause) {
    super(message, cause);
  }
}
