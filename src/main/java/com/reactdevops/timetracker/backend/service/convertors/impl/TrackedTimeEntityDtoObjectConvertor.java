package com.reactdevops.timetracker.backend.service.convertors.impl;

import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.service.convertors.DtoEntityObjectConvertor;
import com.reactdevops.timetracker.backend.web.dto.TrackedTime;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TrackedTimeEntityDtoObjectConvertor
    implements DtoEntityObjectConvertor<TrackedTimeEntity, TrackedTime> {
  @Override
  public TrackedTimeEntity dtoToEntity(TrackedTime object) {
    return null;
  }

  @Override
  public TrackedTime entityToDto(TrackedTimeEntity object) {
    return null;
  }
}
