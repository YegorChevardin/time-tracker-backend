package com.reactdevops.timetracker.backend.repository.dao.impl;

import com.reactdevops.timetracker.backend.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.providers.DataSourceProvider;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class TrackerTimeDAO implements CreateReadDeleteDAO<TrackedTimeEntity> {
  @Inject private DataSourceProvider dataSourceProvider;

  @Override
  public void create(TrackedTimeEntity object) {}

  @Override
  public Optional<TrackedTimeEntity> read(Long id) {
    return Optional.empty();
  }

  @Override
  public List<TrackedTimeEntity> readAll() {
    return null;
  }

  @Override
  public void deleteById(Long id) {}
}
