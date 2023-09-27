package com.reactdevops.timetracker.backend.web.servlets.account;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.exceptions.ObjectNotFoundException;
import com.reactdevops.timetracker.backend.service.qualifiers.UserServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.UserService;
import com.reactdevops.timetracker.backend.web.dto.User;
import com.reactdevops.timetracker.backend.web.helper.ErrorCreatingHelper;
import com.reactdevops.timetracker.backend.web.helper.RequestBodyReader;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "userServlet", value = "/api/v1/users/user")
public class UserServlet extends HttpServlet {
  @Inject @UserServiceQualifier private UserService userService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    PrintWriter printWriter = resp.getWriter();
    Gson gson = new Gson();
    String userId = req.getParameter("id");

    try {
      printWriter.print(gson.toJson(userService.read(Long.valueOf(userId))));
    } catch (ObjectNotFoundException e) {
      resp.setStatus(404);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (CustomWebException e) {
      resp.setStatus(500);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (ClassCastException | NumberFormatException e) {
      resp.setStatus(406);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError("User Id must be valid!")));
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    PrintWriter printWriter = resp.getWriter();
    Gson gson = new Gson();

    try {
      User dataObject = gson.fromJson(RequestBodyReader.readBodyFromRequest(req), User.class);
      userService.create(dataObject);
    } catch (ObjectNotFoundException e) {
      resp.setStatus(404);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (CustomWebException e) {
      resp.setStatus(500);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (JsonSyntaxException e) {
      resp.setStatus(406);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError("User structure must be valid!")));
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    PrintWriter printWriter = resp.getWriter();
    Gson gson = new Gson();
    String userId = req.getParameter("id");

    try {
      userService.deleteById(Long.valueOf(userId));
    } catch (ObjectNotFoundException e) {
      resp.setStatus(404);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (CustomWebException e) {
      resp.setStatus(500);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (ClassCastException e) {
      resp.setStatus(406);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError("User Id must be valid!")));
    }
  }
}
