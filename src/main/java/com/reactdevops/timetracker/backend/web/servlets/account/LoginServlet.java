package com.reactdevops.timetracker.backend.web.servlets.account;

import com.reactdevops.timetracker.backend.service.services.AuthService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/api/v1/auth/login")
public class LoginServlet extends HttpServlet {
  @Inject private AuthService authService;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
  }
}
