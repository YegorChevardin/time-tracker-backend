package com.reactdevops.timetracker.backend.entities;

import java.sql.Timestamp;

/**
 * Tracked time entity
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class TrackedTimeEntity {
  Long id;
  String description;
  Timestamp startTime;
  Timestamp endTime;
  UserEntity userEntity;

  public TrackedTimeEntity(
      Long id, String description, Timestamp startTime, Timestamp endTime, UserEntity userEntity) {
    this.id = id;
    this.description = description;
    this.startTime = startTime;
    this.endTime = endTime;
    this.userEntity = userEntity;
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

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }
}
