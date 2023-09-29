package com.reactdevops.timetracker.backend.service.services;

/**
 * Service for sending reports to the mentor
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface EmailService {
    void sendEmail(String receiverEmail);
}
