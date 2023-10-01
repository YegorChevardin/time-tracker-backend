package com.reactdevops.timetracker.backend.repository.dao;

import com.reactdevops.timetracker.backend.repository.providers.DataSourceProvider;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface for defining CRD operations
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadDeleteDAO<T> {
  void create(T object) throws SQLException;

  Optional<T> read(Long id) throws SQLException;

  List<T> readAll() throws SQLException;

  void deleteById(Long id) throws SQLException;

  void setDataSourceProvider(DataSourceProvider dataSourceProvider);
}
