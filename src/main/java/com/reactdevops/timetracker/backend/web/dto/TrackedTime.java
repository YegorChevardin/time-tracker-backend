package com.reactdevops.timetracker.backend.web.dto;

import java.sql.Timestamp;

/**
 * Tracked time DTO
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class TrackedTime {
  Long id;
  String description;
  Timestamp startTime;
  Timestamp endTime;
  User user;

  public TrackedTime(Long id, String description, Timestamp startTime, Timestamp endTime, User user) {
    this.id = id;
    this.description = description;
    this.startTime = startTime;
    this.endTime = endTime;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
