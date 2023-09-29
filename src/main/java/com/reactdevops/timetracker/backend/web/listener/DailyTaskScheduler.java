package com.reactdevops.timetracker.backend.web.listener;

import com.reactdevops.timetracker.backend.service.qualifiers.EmailServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.EmailService;
import com.reactdevops.timetracker.backend.service.services.impl.UserServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

/**
 * Listener class for executing code every time at 00:00 AM
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
@WebListener
public class DailyTaskScheduler implements ServletContextListener {
  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

  private static final String MENTOR_EMAIL = "egor03052004@gmail.com";

  @Inject @EmailServiceQualifier private EmailService emailService;

  private ScheduledExecutorService scheduler;

  private final Runnable task =
      new Runnable() {
        @Override
        public void run() {
          logger.log(Level.INFO, String.format("Sending report to %s", MENTOR_EMAIL));
          emailService.sendEmail(MENTOR_EMAIL);
        }
      };

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    scheduler = Executors.newScheduledThreadPool(1);
    // long initialDelay = calculateInitialDelay();

    // scheduler.scheduleAtFixedRate(task, initialDelay, 24 * 60 * 60, TimeUnit.SECONDS);
    scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    if (scheduler != null && !scheduler.isShutdown()) {
      scheduler.shutdown();
    }
  }

  //  private long calculateInitialDelay() {
  //    long currentTimeMillis = System.currentTimeMillis();
  //    return 24 * 60 * 60 - (currentTimeMillis / 1000) % (24 * 60 * 60);
  //  }
}
