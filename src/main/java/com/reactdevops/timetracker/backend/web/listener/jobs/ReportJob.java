package com.reactdevops.timetracker.backend.web.listener.jobs;

import com.reactdevops.timetracker.backend.service.qualifiers.EmailServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.EmailService;
import jakarta.inject.Inject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class ReportJob implements Job {
  private static final String MENTOR_EMAIL = "egor03052004@gmail.com";
  private static final Logger logger = LogManager.getLogger(ReportJob.class);

  @Inject @EmailServiceQualifier private EmailService emailService;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
    logger.log(Level.INFO, "Creating and sending reports...");
    emailService.sendEmail(MENTOR_EMAIL);
    // todo paste telegram service action here
  }
}
