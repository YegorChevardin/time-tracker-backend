package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.service.bot.TelegramBot;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import com.reactdevops.timetracker.backend.service.services.TelegramService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramServiceImpl implements TelegramService {

    private PdfService pdfService = new PdfServiceImpl();
    TelegramBot bot = new TelegramBot();

    @Override
    public void sendReport() {
        String chatId = TelegramBot.chatId;
        if(chatId != null){
            SendMessage sendMessage = new SendMessage(chatId, pdfService.createPdfReport());
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
