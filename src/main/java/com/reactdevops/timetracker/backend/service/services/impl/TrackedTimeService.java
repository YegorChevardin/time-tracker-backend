package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.repository.qualifiers.TrackerTimeDAOQualifier;
import com.reactdevops.timetracker.backend.service.convertors.impl.TrackedTimeEntityDtoObjectConvertor;
import com.reactdevops.timetracker.backend.service.qualifiers.TrackedTimeServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.web.dto.TrackedTime;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@TrackedTimeServiceQualifier
@RequestScoped
public class TrackedTimeService implements CreateReadDeleteService<TrackedTime> {
  @Inject
  @TrackerTimeDAOQualifier
  private CreateReadDeleteDAO<TrackedTimeEntity> timeEntityCreateReadDeleteDAO;

  @Inject
  private TrackedTimeEntityDtoObjectConvertor timeConvertor;

  @Override
  public void create(TrackedTime object) throws SQLException {
      TrackedTimeEntity entity = timeConvertor.dtoToEntity(object);
      timeEntityCreateReadDeleteDAO.create(entity);
  }

  @Override
  public TrackedTime read(Long id) throws SQLException {
    return timeConvertor.entityToDto(timeEntityCreateReadDeleteDAO.read(id).orElseThrow());
  }

  @Override
  public List<TrackedTime> readAll() throws SQLException {
    return timeConvertor.listEntityToListDto(timeEntityCreateReadDeleteDAO.readAll());
  }

  @Override
  public void deleteById(Long id) throws SQLException {
    timeEntityCreateReadDeleteDAO.deleteById(id);
  }
}
