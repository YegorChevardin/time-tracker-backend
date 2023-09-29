package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.dao.TimeTrackerCRUDDAO;
import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.repository.qualifiers.TrackerTimeDAOQualifier;
import com.reactdevops.timetracker.backend.service.convertors.impl.TrackedTimeEntityDtoObjectConvertor;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.exceptions.UserIdNotFoundException;
import com.reactdevops.timetracker.backend.service.qualifiers.TrackedTimeServiceQualifier;
import com.reactdevops.timetracker.backend.service.qualifiers.UserServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.service.services.TrackedTimeService;
import com.reactdevops.timetracker.backend.service.services.UserService;
import com.reactdevops.timetracker.backend.web.dto.TrackedTime;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

@TrackedTimeServiceQualifier
@RequestScoped
public class TrackedTimeServiceImpl implements TrackedTimeService {
  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

  @Inject @TrackerTimeDAOQualifier private TimeTrackerCRUDDAO timeEntityCreateReadDeleteDAO;

  @Inject @UserServiceQualifier UserService userService;

  @Inject private TrackedTimeEntityDtoObjectConvertor timeConvertor;

  @Override
  public void create(TrackedTime object) {
    try {
      TrackedTimeEntity entity = timeConvertor.dtoToEntity(object);
      userService.read(entity.getUserEntity().getId());
      if (entity.getUserEntity().getId() == null) {
        throw new UserIdNotFoundException("User id must be not null");
      }
      timeEntityCreateReadDeleteDAO.create(entity);
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Cannot proceed this action! Try again later", e);
    }
  }

  @Override
  public TrackedTime read(Long id) {
    try {
      return timeConvertor.entityToDto(timeEntityCreateReadDeleteDAO.read(id).orElseThrow());
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Cannot proceed this action! Try again later", e);
    }
  }

  @Override
  public List<TrackedTime> readAll() {
    try {
      return timeConvertor.listEntityToListDto(timeEntityCreateReadDeleteDAO.readAll());
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Cannot proceed this action! Try again later", e);
    }
  }

  @Override
  public void deleteById(Long id) {
    try {
      timeEntityCreateReadDeleteDAO.deleteById(id);
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Cannot proceed this action! Try again later", e);
    }
  }

  @Override
  public List<TrackedTime> readAllByUserId(Long userId) {
    try {
      return timeConvertor.listEntityToListDto(
          timeEntityCreateReadDeleteDAO.readAllByUserId(userId));
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new CustomWebException("Cannot proceed this action! Try again later", e);
    }
  }
}
