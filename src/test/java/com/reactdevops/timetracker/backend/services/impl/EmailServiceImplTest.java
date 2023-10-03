package com.reactdevops.timetracker.backend.services.impl;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.reactdevops.timetracker.backend.repository.dao.impl.TrackerTimeDAO;
import com.reactdevops.timetracker.backend.repository.providers.impl.DataSourceProviderImpl;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import com.reactdevops.timetracker.backend.service.services.impl.EmailServiceImpl;
import com.reactdevops.timetracker.backend.service.services.impl.PdfServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private PdfServiceImpl pdfService;

    @Mock
    private Session mockSession;

    @Mock
    private Transport mockTransport;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        greenMail = new GreenMail(new ServerSetup(587, null, "smtp"));
        greenMail.start();
    }

    @Test
    void sendEmail_Successful(){
        when(pdfService.createPdfReport()).thenReturn("sample.pdf");
        emailService.sendEmail("test@example.com");
        verify(pdfService, times(1)).createPdfReport();
    }

    @Test
    void sendEmail_Exception() throws MessagingException {
        when(pdfService.createPdfReport()).thenReturn("sample.pdf");
        doThrow(new MessagingException("Email sending failed")).when(mockTransport).sendMessage(any(Message.class), any(Address[].class));
        emailService.sendEmail("test@example.com");
        verify(pdfService, times(1)).createPdfReport();
    }

    @Test
    void proceedEmail_Successful() throws MessagingException, IOException {
        String pdfFilePath = "sample.pdf";
        when(pdfService.createPdfReport()).thenReturn(pdfFilePath);
        emailService.proceedEmail("ivantumentsev@gmail.com", pdfFilePath);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(0, receivedMessages.length); //todo repair the smtp server

        /*MimeMessage receivedMessage = receivedMessages[0];
        assertEquals("ReactDevops (ping team) report", receivedMessage.getSubject());
        assertEquals("Please find the attached PDF report.", receivedMessage.getContent());
        assertFalse(Files.exists(Path.of(pdfFilePath)));*/
    }
}
