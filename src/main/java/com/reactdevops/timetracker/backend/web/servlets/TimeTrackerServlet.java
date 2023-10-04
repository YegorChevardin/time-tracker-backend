package com.reactdevops.timetracker.backend.web.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.exceptions.ObjectNotFoundException;
import com.reactdevops.timetracker.backend.service.exceptions.UserIdNotFoundException;
import com.reactdevops.timetracker.backend.service.qualifiers.TrackedTimeServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.web.dto.TrackedTime;
import com.reactdevops.timetracker.backend.web.helper.ErrorCreatingHelper;
import com.reactdevops.timetracker.backend.web.helper.RequestBodyReader;
import jakarta.inject.Inject;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.NoSuchElementException;

@WebServlet(name = "timeTracker", value = "/api/v1/tracked-times/tracked-time")
public class TimeTrackerServlet extends HttpServlet {

  @Inject @TrackedTimeServiceQualifier
  private CreateReadDeleteService<TrackedTime> timeCreateReadDeleteService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    PrintWriter pw = resp.getWriter();
    Gson gson = new Gson();
    String trackerId = req.getParameter("id");

    try {
      pw.print(gson.toJson(timeCreateReadDeleteService.read(Long.valueOf(trackerId))));
    } catch (CustomWebException e) {
      resp.setStatus(500);
      pw.print(gson.toJson(ErrorCreatingHelper.createError("The SQL query is wrong!")));
    } catch (NoSuchElementException e) {
      resp.setStatus(500);
      pw.print(
          gson.toJson(ErrorCreatingHelper.createError("There is no such time tracker by this id")));
    } catch (ClassCastException | NumberFormatException e) {
      resp.setStatus(406);
      pw.print(gson.toJson(ErrorCreatingHelper.createError("TimeTracker Id must be valid!")));
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    PrintWriter pw = resp.getWriter();
    Gson gson = new Gson();

    try {
      TrackedTime dataObject =
          gson.fromJson(RequestBodyReader.readBodyFromRequest(req), TrackedTime.class);
      prepareTime(dataObject);
      timeCreateReadDeleteService.create(dataObject);
    } catch (ObjectNotFoundException | UserIdNotFoundException e) {
      resp.setStatus(406);
      pw.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (CustomWebException e) {
      resp.setStatus(500);
      pw.print(
          gson.toJson(
              ErrorCreatingHelper.createError("The SQL query or Json structure is wrong!")));
    } catch (JsonSyntaxException e) {
      resp.setStatus(406);
      pw.print(gson.toJson(ErrorCreatingHelper.createError("Time tracker Json must be valid!")));
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    PrintWriter pw = resp.getWriter();
    Gson gson = new Gson();
    String trackerId = req.getParameter("id");
    try {
      timeCreateReadDeleteService.deleteById(Long.valueOf(trackerId));
    } catch (CustomWebException e) {
      resp.setStatus(500);
      pw.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (ClassCastException e) {
      resp.setStatus(406);
      pw.print(gson.toJson(ErrorCreatingHelper.createError("Time tracker Id must be valid!")));
    }
  }

  private void prepareTime(TrackedTime dataObject) {
    Calendar startTimeCalendar = Calendar.getInstance();
    startTimeCalendar.setTime(dataObject.getStartTime());
    startTimeCalendar.add(Calendar.DAY_OF_MONTH, -1);
    dataObject.setStartTime(new Timestamp(startTimeCalendar.getTimeInMillis()));

    Calendar endTimeCalendar = Calendar.getInstance();
    endTimeCalendar.setTime(dataObject.getEndTime());
    endTimeCalendar.add(Calendar.DAY_OF_MONTH, -1);
    dataObject.setEndTime(new Timestamp(endTimeCalendar.getTimeInMillis()));
  }
}
