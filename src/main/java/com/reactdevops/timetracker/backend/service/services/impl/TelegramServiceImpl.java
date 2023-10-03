package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.TelegramChatCRUDDAO;
import com.reactdevops.timetracker.backend.repository.dao.impl.TelegramChatDAO;
import com.reactdevops.timetracker.backend.repository.entities.TelegramChatEntity;
import com.reactdevops.timetracker.backend.repository.providers.impl.DataSourceProviderImpl;
import com.reactdevops.timetracker.backend.service.bot.TelegramBot;
import com.reactdevops.timetracker.backend.service.qualifiers.TelegramServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.TelegramService;
import jakarta.enterprise.context.RequestScoped;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

@RequestScoped
@TelegramServiceQualifier
public class TelegramServiceImpl implements TelegramService {

    private final PdfServiceImpl pdfService = new PdfServiceImpl();
    private TelegramChatCRUDDAO telegramChatCRUDDAO;


    TelegramBot bot = new TelegramBot();

    public void initService() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot); //todo close the session
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        telegramChatCRUDDAO = new TelegramChatDAO();
        DataSourceProviderImpl dataSourceProvider = new DataSourceProviderImpl();
        dataSourceProvider.reloadDataSource();
        telegramChatCRUDDAO.setDataSourceProvider(dataSourceProvider);
        pdfService.initService();
    }

    @Override
    public void sendReport() {
        initService();
        List<TelegramChatEntity> listOfSubscribed;
        try {
            listOfSubscribed = telegramChatCRUDDAO.readAllChats();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (TelegramChatEntity id : listOfSubscribed){
            String idString = id.getSubscribedChatId().toString();

            SendMessage message = new SendMessage(idString, "Your daily report:");
            File file = new File(pdfService.createPdfReport());
            InputFile inputFile = new InputFile(file);
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(idString);
            sendDocument.setDocument(inputFile);
            try {
                bot.execute(message);
                bot.execute(sendDocument);
                file.delete();
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
