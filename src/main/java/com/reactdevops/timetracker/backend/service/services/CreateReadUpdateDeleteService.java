package com.reactdevops.timetracker.backend.service.services;

/**
 * CRUD interface
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadUpdateDeleteService<T> extends CreateReadDeleteService<T> {
  void update(T object);
}
