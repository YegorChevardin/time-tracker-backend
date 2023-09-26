package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadUpdateDeleteDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.service.services.CreateReadUpdateDeleteService;
import com.reactdevops.timetracker.backend.web.dto.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class UserService implements CreateReadUpdateDeleteService<User> {
  @Inject private CreateReadUpdateDeleteDAO<UserEntity> userEntityCreateReadUpdateDeleteDAO;

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

  @Override
  public void update(User object) {}
}
