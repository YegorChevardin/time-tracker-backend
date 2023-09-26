package com.reactdevops.timetracker.backend.web.servlets.account;

import com.reactdevops.timetracker.backend.service.services.impl.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "userServlet", value = "/api/v1/auth/user")
public class UserServlet extends HttpServlet {
  private final UserService userService;

  @Inject
  public UserServlet(UserService userService) {
    this.userService = userService;
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
