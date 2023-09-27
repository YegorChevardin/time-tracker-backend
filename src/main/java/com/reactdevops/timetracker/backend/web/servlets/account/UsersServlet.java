package com.reactdevops.timetracker.backend.web.servlets.account;

import com.google.gson.Gson;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.qualifiers.UserServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.service.services.UserService;
import com.reactdevops.timetracker.backend.web.dto.User;
import com.reactdevops.timetracker.backend.web.helper.ErrorCreatingHelper;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "usersServlet", value = "/api/v1/users")
public class UsersServlet extends HttpServlet {
  @Inject @UserServiceQualifier private UserService userService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    PrintWriter printWriter = resp.getWriter();
    Gson gson = new Gson();

    try {
      List<User> users = userService.readAll();
      printWriter.print(gson.toJson(users));
    } catch (CustomWebException e) {
      resp.setStatus(500);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    }
  }
}
