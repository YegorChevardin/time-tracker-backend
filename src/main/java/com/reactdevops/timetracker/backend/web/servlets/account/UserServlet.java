package com.reactdevops.timetracker.backend.web.servlets.account;

import com.google.gson.Gson;
import com.reactdevops.timetracker.backend.service.qualifiers.UserServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.CreateReadDeleteService;
import com.reactdevops.timetracker.backend.service.services.impl.UserService;
import com.reactdevops.timetracker.backend.web.dto.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "userServlet", value = "/api/v1/auth/user")
public class UserServlet extends HttpServlet {
  @Inject
  @UserServiceQualifier
  private CreateReadDeleteService<User> userService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");

    PrintWriter printWriter = resp.getWriter();
    Gson gson = new Gson();

    printWriter.print(gson.toJson(userService.read(10L)));
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
