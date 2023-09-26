package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.repository.qualifiers.UserDAOQualifier;
import com.reactdevops.timetracker.backend.service.qualifiers.UserServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.web.dto.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@UserServiceQualifier
@RequestScoped
public class UserService implements CreateReadDeleteService<User> {
  @Inject @UserDAOQualifier
  private CreateReadDeleteDAO<UserEntity> userEntityCreateReadUpdateDeleteDAO;

  @Override
  public void create(User object) {}

  @Override
  public User read(Long id) {
    return new User(10L, "It's works", "password");
  }

  @Override
  public List<User> readAll() {
    return null;
  }

  @Override
  public void deleteById(Long id) {}
}
