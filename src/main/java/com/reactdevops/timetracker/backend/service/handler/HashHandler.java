package com.reactdevops.timetracker.backend.service.handler;

/**
 * Interface for hash functions that will help to hash passwords or tokens
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface HashHandler {
  /** Method for hashing algorithm */
  String hash(String value);

  /** Method to check if given value is hashed value */
  boolean checkEquality(String value, String token);
}
