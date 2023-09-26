package com.reactdevops.timetracker.backend.repository.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface for defining CRD operations
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadDeleteDAO<T> {
  void create(T object);

  Optional<T> read(Long id);

  List<T> readAll();

  void deleteById(Long id);
}
