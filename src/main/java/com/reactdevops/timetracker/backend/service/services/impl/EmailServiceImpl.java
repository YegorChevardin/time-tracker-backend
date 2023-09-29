package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.service.qualifiers.EmailServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.EmailService;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import jakarta.enterprise.context.RequestScoped;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@RequestScoped
@EmailServiceQualifier
public class EmailServiceImpl implements EmailService {
  private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

  String smtpHost = "smtp-relay.sendinblue.com";
  int smtpPort = 587;
  String smtpUser = "egor03052004@gmail.com";
  String smtpPassword = "wHjL58GZcQNJ2qvO";
  String subject = "ReactDevops (ping team) report";
  String body = "Please find the attached PDF report.";

  private PdfService pdfService = new PdfServiceImpl();

  @Override
  public void sendEmail(String receiverEmail) {
    logger.log(Level.INFO, String.format("Sending email to %s...", receiverEmail));
    String pdfFilePath = pdfService.createPdfReport();
    proceedEmail(receiverEmail);
  }

  private void proceedEmail(String receiverEmail) {
    Properties props = new Properties();
    props.put("mail.smtp.host", smtpHost);
    props.put("mail.smtp.port", 587);
    props.put("mail.smtp.auth", "true");

    Session session =
        Session.getDefaultInstance(
            props,
            new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPassword);
              }
            });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(smtpUser));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
      message.setSubject(subject);

      BodyPart textPart = new MimeBodyPart();
      textPart.setText(body);

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(textPart);

      message.setContent(multipart);

      Transport.send(message);

      logger.log(Level.INFO, String.format("Email send successfully to %s!", receiverEmail));
    } catch (MessagingException e) {
      logger.log(Level.ERROR, e.getMessage(), e);
    }
  }
}
