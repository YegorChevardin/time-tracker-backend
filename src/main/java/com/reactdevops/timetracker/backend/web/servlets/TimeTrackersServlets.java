package com.reactdevops.timetracker.backend.web.servlets;

import com.google.gson.Gson;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.qualifiers.TrackedTimeServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.impl.TrackedTimeServiceImpl;
import com.reactdevops.timetracker.backend.web.helper.ErrorCreatingHelper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@WebServlet(name = "timeTrackers", value = "/api/v1/tracked-times")
public class TimeTrackersServlets extends HttpServlet {

    @Inject
    @TrackedTimeServiceQualifier
    private TrackedTimeServiceImpl timeCreateReadDeleteService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        Long userId = Long.valueOf(req.getParameter("userId"));
        Gson gson = new Gson();
        try {
            System.out.println(timeCreateReadDeleteService.readAllByUserId(userId));
            pw.print(gson.toJson(timeCreateReadDeleteService.readAllByUserId(userId)));
        } catch (CustomWebException e) {
            resp.setStatus(500);
            pw.print(gson.toJson(ErrorCreatingHelper.createError("The SQL query is wrong!")));
        } catch (NoSuchElementException e) {
            resp.setStatus(500);
            pw.print(gson.toJson(ErrorCreatingHelper.createError("There is no such time tracker by this id")));
        } catch (ClassCastException | NumberFormatException e) {
            resp.setStatus(406);
            pw.print(gson.toJson(ErrorCreatingHelper.createError("TimeTracker Id must be valid!")));
        }
    }
}
