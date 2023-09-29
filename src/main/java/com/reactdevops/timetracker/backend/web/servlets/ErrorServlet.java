package com.reactdevops.timetracker.backend.web.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ErrorServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");

    PrintWriter writer = resp.getWriter();
    Map<String, String> homeResponse = new HashMap<>();

    homeResponse.put("Error", "Page does not exists");
    writer.print(new Gson().toJson(homeResponse));
  }
}
