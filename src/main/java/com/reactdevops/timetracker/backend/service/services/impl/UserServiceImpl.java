package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.UserCRUDDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.repository.qualifiers.UserDAOQualifier;
import com.reactdevops.timetracker.backend.service.convertors.DtoEntityObjectConvertor;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.exceptions.ObjectExistsException;
import com.reactdevops.timetracker.backend.service.exceptions.ObjectNotFoundException;
import com.reactdevops.timetracker.backend.service.handler.HashHandler;
import com.reactdevops.timetracker.backend.service.qualifiers.PasswordHasherQualifier;
import com.reactdevops.timetracker.backend.service.qualifiers.UserEntityObjectConvertorQualifier;
import com.reactdevops.timetracker.backend.service.qualifiers.UserServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.UserService;
import com.reactdevops.timetracker.backend.web.dto.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

@UserServiceQualifier
@RequestScoped
public class UserServiceImpl implements UserService {
  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

  @Inject @UserDAOQualifier
  private UserCRUDDAO userEntityCreateReadUpdateDeleteDAO;

  @Inject @UserEntityObjectConvertorQualifier
  private DtoEntityObjectConvertor<UserEntity, User> userDtoEntityObjectConvertor;

  @Inject @PasswordHasherQualifier private HashHandler passwordHash;

  @Override
  public void create(User object) {
    try {
      if (userEntityCreateReadUpdateDeleteDAO.findByUsername(object.getUsername()).isPresent()) {
        throw new ObjectExistsException(
            String.format("User with this username (%s) already exists", object.getUsername()));
      }

      object.setPassword(passwordHash.hash(object.getPassword()));
      userEntityCreateReadUpdateDeleteDAO.create(userDtoEntityObjectConvertor.dtoToEntity(object));
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Cannot create this user!", e);
    }
  }

  @Override
  public User read(Long id) {
    try {
      return userDtoEntityObjectConvertor.entityToDto(findByIdOrThrow(id));
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Cannot proceed this action! Try again later", e);
    }
  }

  @Override
  public List<User> readAll() {
    try {
      return userDtoEntityObjectConvertor.listEntityToListDto(
          userEntityCreateReadUpdateDeleteDAO.readAll());
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Something went wrong! Try again later.", e);
    }
  }

  @Override
  public void deleteById(Long id) {
    try {
      findByIdOrThrow(id);
      userEntityCreateReadUpdateDeleteDAO.deleteById(id);
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Something went wrong! Try again later.", e);
    }
  }

  private UserEntity findByIdOrThrow(Long id) throws SQLException {
    return userEntityCreateReadUpdateDeleteDAO
        .read(id)
        .orElseThrow(
            () ->
                new ObjectNotFoundException(
                    String.format("User with this id %s does not exists", id)));
  }

  @Override
  public User findByUsername(String username) {
    try {
      return userDtoEntityObjectConvertor.entityToDto(
          userEntityCreateReadUpdateDeleteDAO
              .findByUsername(username)
              .orElseThrow(
                  () ->
                      new ObjectNotFoundException(
                          String.format("User with this username %s were not found", username))));
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Something went wrong, try again later!");
    }
  }
}
