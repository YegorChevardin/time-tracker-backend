package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.repository.qualifiers.UserDAOQualifier;
import com.reactdevops.timetracker.backend.service.convertors.impl.UserEntityDtoObjectConvertor;
import com.reactdevops.timetracker.backend.service.qualifiers.UserServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.web.dto.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@UserServiceQualifier
@RequestScoped
public class UserService implements CreateReadDeleteService<User> {
  @Inject @UserDAOQualifier
  private CreateReadDeleteDAO<UserEntity> userEntityCreateReadUpdateDeleteDAO;

  @Inject
  private UserEntityDtoObjectConvertor userConvertor;

  @Override
  public void create(User object) throws SQLException {
    UserEntity entity = userConvertor.dtoToEntity(object);
    userEntityCreateReadUpdateDeleteDAO.create(entity);
  }

  @Override
  public User read(Long id) throws SQLException {
    UserEntity entity = userEntityCreateReadUpdateDeleteDAO.read(id).orElseThrow();
    return userConvertor.entityToDto(entity);
  }

  @Override
  public List<User> readAll() throws SQLException {
    return userConvertor.listEntityToListDto(userEntityCreateReadUpdateDeleteDAO.readAll());
  }

  @Override
  public void deleteById(Long id) throws SQLException {
    userEntityCreateReadUpdateDeleteDAO.deleteById(id);
  }
}
