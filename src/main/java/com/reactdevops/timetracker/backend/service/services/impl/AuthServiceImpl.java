package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.service.services.AuthService;
import com.reactdevops.timetracker.backend.web.dto.auth.JwtResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class AuthServiceImpl implements AuthService {
  private final CreateReadDeleteDAO<UserEntity> userEntityCreateReadDeleteDAO;

  @Inject
  public AuthServiceImpl(CreateReadDeleteDAO<UserEntity> userEntityCreateReadDeleteDAO) {
    this.userEntityCreateReadDeleteDAO = userEntityCreateReadDeleteDAO;
  }

  @Override
  public JwtResponse processLogin(JwtResponse jwtResponse) {
    return null;
  }
}
