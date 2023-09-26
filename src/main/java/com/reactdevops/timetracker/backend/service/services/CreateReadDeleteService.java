package com.reactdevops.timetracker.backend.service.services;

import java.sql.SQLException;
import java.util.List;

/**
 * CRD interface
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadDeleteService<T> {
  void create(T object) throws SQLException;

  T read(Long id) throws SQLException;

  List<T> readAll() throws SQLException;

  void deleteById(Long id) throws SQLException;
}
