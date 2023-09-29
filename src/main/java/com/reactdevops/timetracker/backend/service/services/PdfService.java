package com.reactdevops.timetracker.backend.service.services;

/**
 * Service for creating pdf file report
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface PdfService {
  /** Creates pdf document with report and returns file path */
  String createPdfReport();
}
