package com.reactdevops.timetracker.backend.web.dto.auth;

/**
 * Dto object for login request
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class JwtRequest {
  private String username;
  private String password;

  public JwtRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
