package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.service.qualifiers.EmailServiceQualifier;
import com.reactdevops.timetracker.backend.service.qualifiers.PdfServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.EmailService;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
@EmailServiceQualifier
public class EmailServiceImpl implements EmailService {
    @Inject
    @PdfServiceQualifier
    private PdfService pdfService;

    @Override
    public void sendEmail(String receiverEmail) {
        String pdfFilePath = pdfService.createPdfReport();
        //todo
    }
}
