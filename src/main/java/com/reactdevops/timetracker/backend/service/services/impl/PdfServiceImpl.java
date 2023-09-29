package com.reactdevops.timetracker.backend.service.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.impl.UserDAO;
import com.reactdevops.timetracker.backend.repository.providers.impl.DataSourceProviderImpl;
import com.reactdevops.timetracker.backend.service.qualifiers.PdfServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.PdfService;
import com.reactdevops.timetracker.backend.web.listener.jobs.ReportJob;
import jakarta.enterprise.context.RequestScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequestScoped
@PdfServiceQualifier
public class PdfServiceImpl implements PdfService {
  private static final Logger logger = LogManager.getLogger(ReportJob.class);

  private UserDAO userDAO = new UserDAO();

  private void initService() {
    DataSourceProviderImpl dataSourceProvider = new DataSourceProviderImpl();
    dataSourceProvider.reloadDataSource();
    userDAO.setDataSourceProvider(dataSourceProvider);
  }

  @Override
  public String createPdfReport() {
    initService();
    //todo create pdf
    return null;
  }
}
