package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.service.exceptions.AuthException;
import com.reactdevops.timetracker.backend.service.handler.HashHandler;
import com.reactdevops.timetracker.backend.service.handler.impl.JwtHandler;
import com.reactdevops.timetracker.backend.service.qualifiers.JwtTokenAuthServiceQualifier;
import com.reactdevops.timetracker.backend.service.qualifiers.PasswordHasherQualifier;
import com.reactdevops.timetracker.backend.service.qualifiers.UserServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.AuthService;
import com.reactdevops.timetracker.backend.service.services.UserService;
import com.reactdevops.timetracker.backend.web.dto.User;
import com.reactdevops.timetracker.backend.web.dto.auth.JwtRequest;
import com.reactdevops.timetracker.backend.web.dto.auth.JwtResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@JwtTokenAuthServiceQualifier
@RequestScoped
public class AuthServiceImpl implements AuthService {
  private static final int TOKEN_HEADER_BEGIN_INDEX = 7;

  @Inject @UserServiceQualifier private UserService userService;
  @Inject @PasswordHasherQualifier private HashHandler passwordHashHandler;

  @Override
  public JwtResponse processLogin(JwtRequest jwtRequest) {
    User user = userService.findByUsername(jwtRequest.getUsername());
    if (!passwordHashHandler.checkEquality(jwtRequest.getPassword(), user.getPassword())) {
      throw new AuthException("Wrong username or password!");
    }

    JwtHandler jwtHandler = new JwtHandler();

    String token = jwtHandler.generateToken(user.getUsername());

    return new JwtResponse(user, token);
  }

  @Override
  public boolean verify(String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(TOKEN_HEADER_BEGIN_INDEX);
      JwtHandler jwtHandler = new JwtHandler();
      String username;

      try {
        username = jwtHandler.getUsernameFromToken(token);
      } catch (IllegalArgumentException e) {
        throw new AuthException("Unable to get jwt token", e);
      } catch (ExpiredJwtException e) {
        throw new AuthException("Token has been expired!", e);
      }

      User user = userService.findByUsername(username);

      return jwtHandler.validateToken(token, user.getUsername());
    }
    throw new AuthException("Jwt token doesn't starts with bearer or equals null");
  }

  @Override
  public String getUsernameFromToken(String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(TOKEN_HEADER_BEGIN_INDEX);
      JwtHandler jwtHandler = new JwtHandler();

      return jwtHandler.getUsernameFromToken(token);
    }
    throw new AuthException("Jwt token doesn't starts with bearer or equals null");
  }
}
