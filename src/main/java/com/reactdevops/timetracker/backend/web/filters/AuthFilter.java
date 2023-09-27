package com.reactdevops.timetracker.backend.web.filters;

import com.google.gson.Gson;
import com.reactdevops.timetracker.backend.service.exceptions.AuthException;
import com.reactdevops.timetracker.backend.service.qualifiers.JwtTokenAuthServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.AuthService;
import com.reactdevops.timetracker.backend.web.helper.ErrorCreatingHelper;
import io.jsonwebtoken.SignatureException;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Authentication filter
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
@WebFilter(
    filterName = "AuthFilter",
    urlPatterns = {"/*"})
public class AuthFilter implements Filter {
  private static final String AUTH_PATH = "/api/v1/auth/login";
  private static final String REGISTER_PATH = "/api/v1/users/user";

  @Inject @JwtTokenAuthServiceQualifier private AuthService authService;

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
    String requestMethod = ((HttpServletRequest) servletRequest).getMethod();

    if (requestURI.equalsIgnoreCase(AUTH_PATH)
        || (requestURI.equalsIgnoreCase(REGISTER_PATH) && requestMethod.equalsIgnoreCase("POST"))) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
      String authHeader = httpServletRequest.getHeader("Authorization");

      try {
        if (authService.verify(authHeader)) {
          filterChain.doFilter(servletRequest, servletResponse);
        } else {
          sendJsonErrorResponse(httpServletResponse, "Jwt token is not valid");
        }
      } catch (AuthException e) {
        sendJsonErrorResponse(httpServletResponse, e.getMessage());
      } catch (SignatureException e) {
        sendJsonErrorResponse(httpServletResponse, "Jwt token is not valid");
      }
    }
  }

  private void sendJsonErrorResponse(HttpServletResponse response, String message)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");

    Gson gson = new Gson();
    PrintWriter out = response.getWriter();

    out.print(gson.toJson(ErrorCreatingHelper.createError(message)));
  }
}
