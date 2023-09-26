package com.reactdevops.timetracker.backend.web.servlets;

import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.web.dto.TrackedTime;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "timeTracker", value = "/api/v1/tracked-time")
public class TimeTrackerServlet extends HttpServlet {
  private final CreateReadDeleteService<TrackedTime> timeCreateReadDeleteService;

  @Inject
  public TimeTrackerServlet(CreateReadDeleteService<TrackedTime> timeCreateReadDeleteService) {
    this.timeCreateReadDeleteService = timeCreateReadDeleteService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doGet(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doDelete(req, resp);
  }
}
