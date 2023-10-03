package com.reactdevops.timetracker.backend.web.listener;

import com.reactdevops.timetracker.backend.web.listener.jobs.ReportJob;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Listener class for executing code every time at 00:00 AM
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
@WebListener
public class DailyTaskScheduler implements ServletContextListener {
  private static final Logger logger = LogManager.getLogger(DailyTaskScheduler.class);

  private Scheduler scheduler;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      scheduler = new StdSchedulerFactory().getScheduler();

      JobDetail jobReportDetail =
          JobBuilder.newJob(ReportJob.class).withIdentity("reportDailyJob").build();

      //For every minute use: 0 * * ? * *. For 23 o'clock - 0 00 23 ? * *
      CronTrigger cronDailyReportSender =
          TriggerBuilder.newTrigger()
              .withIdentity("reportDailyJobTrigger")
              .startNow()
              .withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * *"))
              .build();

      scheduler.scheduleJob(jobReportDetail, cronDailyReportSender);

      scheduler.start();
    } catch (SchedulerException e) {
      logger.log(Level.ERROR, e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    if (scheduler != null) {
      try {
        scheduler.shutdown();
      } catch (SchedulerException e) {
        logger.log(Level.ERROR, e.getMessage(), e);
        throw new RuntimeException(e);
      }
    }
  }
}
