package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.service.qualifiers.PdfServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
@PdfServiceQualifier
public class PdfServiceImpl implements PdfService {
    @Override
    public String createPdfReport() {
        return null;
    }
}
