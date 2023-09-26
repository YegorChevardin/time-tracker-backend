package com.reactdevops.timetracker.backend.repository.dao;

/**
 * Interface for defining CRUD operations
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadUpdateDeleteDAO<T> extends CreateReadDeleteDAO<T> {
  void update(T object);
}
