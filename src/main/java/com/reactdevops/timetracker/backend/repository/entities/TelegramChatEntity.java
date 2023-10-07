package com.reactdevops.timetracker.backend.repository.entities;

public class TelegramChatEntity {
    Long subscribedChatId;

    public TelegramChatEntity(Long subscribedChatId) {
        this.subscribedChatId = subscribedChatId;
    }

    public Long getSubscribedChatId() {
        return subscribedChatId;
    }

    public void setSubscribedChatId(Long subscribedChatId) {
        this.subscribedChatId = subscribedChatId;
    }
}
