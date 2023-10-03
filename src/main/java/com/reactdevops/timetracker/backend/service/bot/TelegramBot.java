package com.reactdevops.timetracker.backend.service.bot;

import com.reactdevops.timetracker.backend.repository.dao.TelegramChatCRUDDAO;
import com.reactdevops.timetracker.backend.repository.dao.impl.TelegramChatDAO;
import com.reactdevops.timetracker.backend.repository.entities.TelegramChatEntity;
import com.reactdevops.timetracker.backend.repository.providers.impl.DataSourceProviderImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.SQLException;

public class TelegramBot extends TelegramLongPollingBot {

    private TelegramChatCRUDDAO telegramChatCRUDDAO;

    public void initService() {
        telegramChatCRUDDAO = new TelegramChatDAO();
        DataSourceProviderImpl dataSourceProvider = new DataSourceProviderImpl();
        dataSourceProvider.reloadDataSource();
        telegramChatCRUDDAO.setDataSourceProvider(dataSourceProvider);
    }

    @Override
    public String getBotUsername() {
        return "ReactDevOpsBot";
    }

    @Override
    public String getBotToken() {
        return "6487313545:AAFWTraKVr0ypsgZ40yWmtapi-4yFkf0sEM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        initService();
        Long id = update.getMessage().getChatId();
        try {
            if(telegramChatCRUDDAO.notExistsById(id)) {
                telegramChatCRUDDAO.create(new TelegramChatEntity(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
