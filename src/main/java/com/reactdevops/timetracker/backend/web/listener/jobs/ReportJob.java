package com.reactdevops.timetracker.backend.web.listener.jobs;

import com.reactdevops.timetracker.backend.service.bot.TelegramBot;
import com.reactdevops.timetracker.backend.service.services.EmailService;
import com.reactdevops.timetracker.backend.service.services.impl.EmailServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class ReportJob implements Job {
  private static final String MENTOR_EMAIL = "egor03052004@gmail.com";
  private static final Logger logger = LogManager.getLogger(ReportJob.class);

  private EmailService emailService = new EmailServiceImpl();
  private TelegramBot bot = new TelegramBot();

  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
    logger.log(Level.INFO, "Creating and sending reports...");
    try {
      emailService.sendEmail(MENTOR_EMAIL);
    } catch (Exception e) {
      logger.log(Level.ERROR, e.getMessage(), e);
      throw e;
    }
    bot.sendReport();
  }
}
