package com.reactdevops.timetracker.backend.repository.dao.impl;

import com.reactdevops.timetracker.backend.repository.dao.TelegramChatCRUDDAO;
import com.reactdevops.timetracker.backend.repository.entities.TelegramChatEntity;
import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.repository.providers.DataSourceProvider;
import com.reactdevops.timetracker.backend.repository.qualifiers.TelegramChatDaoQualifier;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TelegramChatDaoQualifier
@RequestScoped
public class TelegramChatDAO implements TelegramChatCRUDDAO {
    @Inject
    private DataSourceProvider dataSourceProvider;

    private static final String FIND_ALL_SUBSCRIBED_CHATS =
            "SELECT * FROM telegram_chats";
    private static final String CREATE_SUBSCRIBED_CHAT = "INSERT INTO telegram_chats(subscribed_chat_id) VALUES (?);";
    private static final String CHECK_IF_EXISTS_BY_ID = "SELECT EXISTS (SELECT 1 FROM telegram_chats WHERE subscribed_chat_id = (?));";

    @Override
    public List<TelegramChatEntity> readAllChats() throws SQLException {
        List<TelegramChatEntity> entityList = new ArrayList<>();

        try (Connection connection = dataSourceProvider.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_SUBSCRIBED_CHATS)) {
            while (resultSet.next()) {
                entityList.add(
                        new TelegramChatEntity(
                                resultSet.getLong("subscribed_chat_id")));
            }
        }
        return entityList;
    }

    @Override
    public boolean notExistsById(Long id) throws SQLException {
        try(Connection connection = dataSourceProvider.getDataSource().getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_IF_EXISTS_BY_ID);
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void prepareStatementFromObject(
            PreparedStatement preparedStatement, TelegramChatEntity object) throws SQLException {
        int index = 0;
        preparedStatement.setLong(++index, object.getSubscribedChatId());
    }

    @Override
    public void create(TelegramChatEntity object) throws SQLException {
        try(Connection connection = dataSourceProvider.getDataSource().getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SUBSCRIBED_CHAT);
            prepareStatementFromObject(preparedStatement, object);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Optional<TelegramChatEntity> read(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<TelegramChatEntity> readAll() throws SQLException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws SQLException {

    }

    @Override
    public void setDataSourceProvider(DataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }
}
