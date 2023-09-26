package com.reactdevops.timetracker.backend.repository.dao.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.repository.providers.DataSourceProvider;
import com.reactdevops.timetracker.backend.repository.qualifiers.UserDAOQualifier;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

/**
 * User DAO
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
@UserDAOQualifier
@RequestScoped
public class UserDAO implements CreateReadDeleteDAO<UserEntity> {
  @Inject private DataSourceProvider dataSourceProvider;

  @Override
  public void create(UserEntity object) {}

  @Override
  public Optional<UserEntity> read(Long id) {
    return Optional.empty();
  }

  @Override
  public List<UserEntity> readAll() {
    return null;
  }

  @Override
  public void deleteById(Long id) {}
}
