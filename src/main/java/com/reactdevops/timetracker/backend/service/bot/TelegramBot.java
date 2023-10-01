package com.reactdevops.timetracker.backend.service.bot;

import com.reactdevops.timetracker.backend.repository.dao.impl.TrackerTimeDAO;
import com.reactdevops.timetracker.backend.repository.providers.impl.DataSourceProviderImpl;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import com.reactdevops.timetracker.backend.service.services.impl.PdfServiceImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;

public class TelegramBot extends TelegramLongPollingBot {

    private TrackerTimeDAO timeTrackerCRUDDAO;
    private final PdfService pdfService = new PdfServiceImpl();

    public static String chatId;

    public void initService() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        timeTrackerCRUDDAO = new TrackerTimeDAO();
        DataSourceProviderImpl dataSourceProvider = new DataSourceProviderImpl();
        dataSourceProvider.reloadDataSource();
        timeTrackerCRUDDAO.setDataSourceProvider(dataSourceProvider);
    }


    @Override
    public void onUpdateReceived(Update update) {
        chatId = update.getMessage().getChatId().toString();
    }

    public void sendReport(){
        initService();
        File file = new File(pdfService.createPdfReport());
        if(chatId != null){
            SendDocument sendDocument = new SendDocument(chatId, new InputFile(file));
            try {
                execute(sendDocument);
                file.delete();
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "ReactDevOpsBot";
    }

    @Override
    public String getBotToken() {
        return "6487313545:AAFWTraKVr0ypsgZ40yWmtapi-4yFkf0sEM";
    }
}
