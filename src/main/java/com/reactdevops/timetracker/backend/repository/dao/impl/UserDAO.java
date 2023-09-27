package com.reactdevops.timetracker.backend.repository.dao.impl;

import com.reactdevops.timetracker.backend.repository.dao.CreateReadDeleteDAO;
import com.reactdevops.timetracker.backend.repository.dao.UserCRUDDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.repository.providers.DataSourceProvider;
import com.reactdevops.timetracker.backend.repository.qualifiers.UserDAOQualifier;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User DAO
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
@UserDAOQualifier
@RequestScoped
public class UserDAO implements UserCRUDDAO {
  private static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
  private static final String FIND_ALL = "SELECT * FROM users;";
  private static final String ADD_USER = "INSERT INTO users (username, password) VALUES (?, ?);";
  private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?;";
  private static final String FIND_BY_NAME = "SELECT * FROM users WHERE username = ?;";

  @Inject private DataSourceProvider dataSourceProvider;

  @Override
  public void create(UserEntity object) throws SQLException {
    try (Connection connection = dataSourceProvider.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
      int count = 0;
      preparedStatement.setString(++count, object.getUsername());
      preparedStatement.setString(++count, object.getPassword());
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public Optional<UserEntity> read(Long id) throws SQLException {
    Optional<UserEntity> userEntity = Optional.empty();
    try (Connection connection = dataSourceProvider.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        userEntity =
            Optional.of(
                new UserEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")));
      }
    }
    return userEntity;
  }

  @Override
  public List<UserEntity> readAll() throws SQLException {
    List<UserEntity> userEntities = new ArrayList<>();
    try (Connection connection = dataSourceProvider.getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
      while (resultSet.next()) {
        userEntities.add(
            new UserEntity(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("password")));
      }
    }
    return userEntities;
  }

  @Override
  public void deleteById(Long id) throws SQLException {
    try (Connection connection = dataSourceProvider.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
      preparedStatement.setLong(1, id);
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public Optional<UserEntity> findByUsername(String username) throws SQLException {
    Optional<UserEntity> userEntity = Optional.empty();
    try (Connection connection = dataSourceProvider.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
      preparedStatement.setString(1, username);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        userEntity =
            Optional.of(
                new UserEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")));
      }
    }
    return userEntity;
  }
}
