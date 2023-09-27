package com.reactdevops.timetracker.backend.service.services;

import com.reactdevops.timetracker.backend.web.dto.auth.JwtRequest;
import com.reactdevops.timetracker.backend.web.dto.auth.JwtResponse;

/**
 * Interface for Authentication
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface AuthService {
  JwtResponse processLogin(JwtRequest jwtRequest);

  boolean verify(String token);

  String getUsernameFromToken(String token);
}
