package com.reactdevops.timetracker.backend.web.listener.jobs;

import com.reactdevops.timetracker.backend.service.services.EmailService;
import com.reactdevops.timetracker.backend.service.services.TelegramService;
import com.reactdevops.timetracker.backend.service.services.impl.EmailServiceImpl;
import com.reactdevops.timetracker.backend.service.services.impl.TelegramServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class ReportJob implements Job {
  private static final String MENTOR_EMAIL = "egor03052004@gmail.com";
  private static final Logger logger = LogManager.getLogger(ReportJob.class);

  private EmailService emailService = new EmailServiceImpl();
  private TelegramService telegramService = new TelegramServiceImpl();

  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
    logger.log(Level.INFO, "Creating and sending reports...");
    try {
      emailService.sendEmail(MENTOR_EMAIL);
    } catch (Exception e) {
      logger.log(Level.ERROR, e.getMessage(), e);
      throw e;
    }
    telegramService.sendReport();
  }
}
