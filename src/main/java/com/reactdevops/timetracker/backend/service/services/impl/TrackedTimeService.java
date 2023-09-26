package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.web.dto.TrackedTime;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class TrackedTimeService implements CreateReadDeleteService<TrackedTime> {
  @Inject
  private CreateReadDeleteDAO<TrackedTimeEntity> timeEntityCreateReadDeleteDAO;

  @Override
  public void create(TrackedTime object) {}

  @Override
  public TrackedTime read(Long id) {
    return null;
  }

  @Override
  public List<TrackedTime> readAll() {
    return null;
  }

  @Override
  public void deleteById(Long id) {}
}
