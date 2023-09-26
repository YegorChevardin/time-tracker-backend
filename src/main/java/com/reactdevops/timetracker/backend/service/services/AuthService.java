package com.reactdevops.timetracker.backend.service.services;

import com.reactdevops.timetracker.backend.web.dto.auth.JwtResponse;

/**
 * Interface for Authentication
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface AuthService {
  JwtResponse processLogin(JwtResponse jwtResponse);
}
