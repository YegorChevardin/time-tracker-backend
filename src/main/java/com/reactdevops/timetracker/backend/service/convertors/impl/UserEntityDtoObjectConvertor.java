package com.reactdevops.timetracker.backend.service.convertors.impl;

import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.service.convertors.DtoEntityObjectConvertor;
import com.reactdevops.timetracker.backend.web.dto.User;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UserEntityDtoObjectConvertor implements DtoEntityObjectConvertor<UserEntity, User> {
  @Override
  public UserEntity dtoToEntity(User object) {
    return new UserEntity(object.getId(), object.getUsername(), object.getPassword());
  }

  @Override
  public User entityToDto(UserEntity object) {
    return new User(object.getId(), object.getUsername(), object.getPassword());
  }
}
