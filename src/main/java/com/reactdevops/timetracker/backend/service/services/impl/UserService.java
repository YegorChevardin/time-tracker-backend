package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.web.dto.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class UserService implements CreateReadDeleteService<User> {
  private final CreateReadDeleteDAO<UserEntity> userEntityCreateReadUpdateDeleteDAO;

  @Inject
  public UserService(CreateReadDeleteDAO<UserEntity> userEntityCreateReadUpdateDeleteDAO) {
    this.userEntityCreateReadUpdateDeleteDAO = userEntityCreateReadUpdateDeleteDAO;
  }

  @Override
  public void create(User object) {}

  @Override
  public User read(Long id) {
    return null;
  }

  @Override
  public List<User> readAll() {
    return null;
  }

  @Override
  public void deleteById(Long id) {}
}
