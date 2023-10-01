package com.reactdevops.timetracker.backend.repository.dao;

import com.reactdevops.timetracker.backend.repository.entities.UserEntity;

import java.sql.SQLException;
import java.util.Optional;

/**
 * DAO with user functionality
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface UserCRUDDAO extends CreateReadDeleteDAO<UserEntity> {
  Optional<UserEntity> findByUsername(String username) throws SQLException;
}
