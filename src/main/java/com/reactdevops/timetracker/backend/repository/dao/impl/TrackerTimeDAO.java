package com.reactdevops.timetracker.backend.repository.dao.impl;

import com.reactdevops.timetracker.backend.repository.dao.TimeTrackerCRUDDAO;
import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.repository.providers.DataSourceProvider;
import com.reactdevops.timetracker.backend.repository.qualifiers.TrackerTimeDAOQualifier;
import com.reactdevops.timetracker.backend.repository.qualifiers.UserDAOQualifier;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Tracked Time DAO
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
@TrackerTimeDAOQualifier
@RequestScoped
public class TrackerTimeDAO implements TimeTrackerCRUDDAO {
    @Inject
    private DataSourceProvider dataSourceProvider;

    @Inject
    @UserDAOQualifier
    private UserDAO userDAO;

    private static final String CREATE_TRACKED_TIME_STATEMENT = "INSERT INTO tracked_times(description, start_time, end_time, user_id) VALUES (?, ?, ?, ?);";
    private static final String FIND_ALL_TRACKED_TIMES = "SELECT * FROM tracked_times where user_id = ?;";
    private static final String FIND_BY_USER_ID = "SELECT * FROM tracked_times WHERE id = ?;";
    private static final String DELETE_BY_ID = "DELETE FROM tracked_times WHERE id = ?;";


    @Override
    public void create(TrackedTimeEntity object) throws SQLException {
        try (Connection connection = dataSourceProvider.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TRACKED_TIME_STATEMENT);
            prepareStatementFromObject(preparedStatement, object);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Optional<TrackedTimeEntity> read(Long id) throws SQLException {
      Optional<TrackedTimeEntity> optionalTimeEntity = Optional.empty();

      try (Connection connection = dataSourceProvider.getDataSource().getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID)) {
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            long userId = resultSet.getLong("user_id");
            UserEntity userEntity = userDAO.read(userId).orElse(new UserEntity(id, "user", "password"));

            optionalTimeEntity =
                  Optional.of(
                          new TrackedTimeEntity(
                                  resultSet.getLong("id"),
                                  resultSet.getString("description"),
                                  resultSet.getTimestamp("start_time"),
                                  resultSet.getTimestamp("end_time"),
                                  userEntity));
        }
      }
      return optionalTimeEntity;
    }

    @Override
    public List<TrackedTimeEntity> readAll() throws SQLException {
        List<TrackedTimeEntity> entityList = new ArrayList<>();

        try (Connection connection = dataSourceProvider.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_TRACKED_TIMES)) {
            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                UserEntity userEntity = userDAO.read(userId).orElse(new UserEntity(1L, "Vania", "password"));

                entityList.add(
                        new TrackedTimeEntity(
                                resultSet.getLong("id"),
                                resultSet.getString("description"),
                                resultSet.getTimestamp("start_time"),
                                resultSet.getTimestamp("end_time"),
                                userEntity));
            }
        }
        return entityList;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
      Connection connection = dataSourceProvider.getDataSource().getConnection();
      connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
      connection.setAutoCommit(false);
      try{
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
          preparedStatement.setLong(1, id);
          preparedStatement.executeUpdate();
        }
        connection.commit();
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      } finally {
        connection.setAutoCommit(true);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.close();
      }
    }

    private void prepareStatementFromObject(PreparedStatement preparedStatement, TrackedTimeEntity object) throws SQLException {
        int index = 0;
        preparedStatement.setString(++index, object.getDescription());
        preparedStatement.setTimestamp(++index, object.getStartTime());
        preparedStatement.setTimestamp(++index, object.getEndTime());
        preparedStatement.setLong(++index, object.getUserEntity().getId());
    }

    @Override
    public List<TrackedTimeEntity> readAllByUserId(Long userId) throws SQLException {
        List<TrackedTimeEntity> entityList = new ArrayList<>();

        try (Connection connection = dataSourceProvider.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TRACKED_TIMES)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserEntity userEntity = userDAO.read(userId).orElse(new UserEntity(1L, "Vania", "password"));
                entityList.add(
                        new TrackedTimeEntity(
                                resultSet.getLong("id"),
                                resultSet.getString("description"),
                                resultSet.getTimestamp("start_time"),
                                resultSet.getTimestamp("end_time"),
                                userEntity));
            }
        }
        return entityList;
    }
}

