package com.reactdevops.timetracker.backend.repository.dao;

import com.reactdevops.timetracker.backend.repository.entities.TelegramChatEntity;

import java.sql.SQLException;
import java.util.List;

public interface TelegramChatCRUDDAO extends CreateReadDeleteDAO<TelegramChatEntity> {

    List<TelegramChatEntity> readAllChats() throws SQLException;

    boolean notExistsById(Long id) throws SQLException;

}
