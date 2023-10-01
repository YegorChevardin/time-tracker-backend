package com.reactdevops.timetracker.backend.web.servlets.account;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.reactdevops.timetracker.backend.service.exceptions.AuthException;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.qualifiers.JwtTokenAuthServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.AuthService;
import com.reactdevops.timetracker.backend.web.dto.User;
import com.reactdevops.timetracker.backend.web.dto.auth.JwtRequest;
import com.reactdevops.timetracker.backend.web.dto.auth.JwtResponse;
import com.reactdevops.timetracker.backend.web.helper.ErrorCreatingHelper;
import com.reactdevops.timetracker.backend.web.helper.RequestBodyReader;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", value = "/api/v1/auth/login")
public class LoginServlet extends HttpServlet {
  @Inject @JwtTokenAuthServiceQualifier private AuthService authService;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("application/json");
    PrintWriter printWriter = resp.getWriter();
    Gson gson = new Gson();

    try {
      JwtRequest jwtRequest = gson.fromJson(RequestBodyReader.readBodyFromRequest(req), JwtRequest.class);
      JwtResponse jwtResponse = authService.processLogin(jwtRequest);
      printWriter.print(gson.toJson(jwtResponse));
    } catch (JsonSyntaxException e) {
      resp.setStatus(406);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError("Jwt request structure must be valid!")));
    } catch (AuthException e) {
      resp.setStatus(403);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    } catch (CustomWebException e) {
      resp.setStatus(500);
      printWriter.print(gson.toJson(ErrorCreatingHelper.createError(e.getMessage())));
    }
  }
}
