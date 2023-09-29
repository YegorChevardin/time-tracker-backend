package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.impl.TrackerTimeDAO;
import com.reactdevops.timetracker.backend.repository.providers.impl.DataSourceProviderImpl;
import com.reactdevops.timetracker.backend.service.qualifiers.PdfServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import com.reactdevops.timetracker.backend.web.listener.jobs.ReportJob;
import jakarta.enterprise.context.RequestScoped;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.tablesaw.api.Table;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

@RequestScoped
@PdfServiceQualifier
public class PdfServiceImpl implements PdfService {
  public static final String PDF_FILE_PATH = "./report.csv";
  private static final Logger logger = LogManager.getLogger(ReportJob.class);

  private TrackerTimeDAO timeTrackerCRUDDAO;

  private void initService() {
    timeTrackerCRUDDAO = new TrackerTimeDAO();
    DataSourceProviderImpl dataSourceProvider = new DataSourceProviderImpl();
    dataSourceProvider.reloadDataSource();
    timeTrackerCRUDDAO.setDataSourceProvider(dataSourceProvider);
  }

  @Override
  public String createPdfReport() {
    initService();
    try {
      Table table = timeTrackerCRUDDAO.readReport();
      recordToPDF(table);
      return PDF_FILE_PATH;
    } catch (SQLException e) {
      logger.log(Level.FATAL, e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  private void recordToPDF(Table table) {
    try(FileWriter fileWriter = new FileWriter(PDF_FILE_PATH)) {
      table.write().csv(fileWriter);
      logger.log(Level.INFO, "Report created successfully...");
    } catch (IOException e) {
      logger.log(Level.ERROR, e.getMessage(), e);
    }
  }
}
