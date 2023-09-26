package com.reactdevops.timetracker.backend.service.convertors.impl;

import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.service.convertors.DtoEntityObjectConvertor;
import com.reactdevops.timetracker.backend.service.qualifiers.UserEntityObjectConvertorQualifier;
import com.reactdevops.timetracker.backend.web.dto.TrackedTime;
import com.reactdevops.timetracker.backend.web.dto.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class TrackedTimeEntityDtoObjectConvertor implements DtoEntityObjectConvertor<TrackedTimeEntity, TrackedTime> {

  @Inject
  @UserEntityObjectConvertorQualifier
  private DtoEntityObjectConvertor<UserEntity, User> convertor;

  @Override
  public TrackedTimeEntity dtoToEntity(TrackedTime object) {
    UserEntity userEntity = convertor.dtoToEntity(object.getUser());
    return new TrackedTimeEntity(object.getId(), object.getDescription(), object.getStartTime(), object.getEndTime(), userEntity);
  }

  @Override
  public TrackedTime entityToDto(TrackedTimeEntity object) {
    User user = convertor.entityToDto(object.getUserEntity());
    return new TrackedTime(object.getId(),object.getDescription(), object.getStartTime(), object.getEndTime(), user);
  }
}
