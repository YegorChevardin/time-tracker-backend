package com.reactdevops.timetracker.backend.web;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet for checking server work status
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
@WebServlet(name = "homeServlet", value = "/")
public class HomeServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");

    PrintWriter writer = resp.getWriter();
    Map<String, Timestamp> homeResponse = new HashMap<>();

    homeResponse.put("Response at", Timestamp.valueOf(LocalDateTime.now()));
    writer.print(new Gson().toJson(homeResponse));
  }
}
