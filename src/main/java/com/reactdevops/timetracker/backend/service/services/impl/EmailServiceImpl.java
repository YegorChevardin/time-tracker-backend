package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.service.qualifiers.EmailServiceQualifier;
import com.reactdevops.timetracker.backend.service.qualifiers.PdfServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.EmailService;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequestScoped
@EmailServiceQualifier
public class EmailServiceImpl implements EmailService {
  private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

  @Inject @PdfServiceQualifier private PdfService pdfService;

  @Override
  public void sendEmail(String receiverEmail) {
    logger.log(Level.INFO, String.format("Send email to %s", receiverEmail));
  }
}
