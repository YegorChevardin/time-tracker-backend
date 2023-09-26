package com.reactdevops.timetracker.backend.web.dto.auth;

import com.reactdevops.timetracker.backend.web.dto.User;

/**
 * Jwt response for successful login
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class JwtResponse {
  private User user;
  private String jwtToken;

  public JwtResponse(User user, String jwtToken) {
    this.user = user;
    this.jwtToken = jwtToken;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }
}
