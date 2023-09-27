package com.reactdevops.timetracker.backend.web.filters;

import com.google.gson.Gson;
import com.reactdevops.timetracker.backend.service.exceptions.AuthException;
import com.reactdevops.timetracker.backend.service.qualifiers.JwtTokenAuthServiceQualifier;
import com.reactdevops.timetracker.backend.service.services.AuthService;
import com.reactdevops.timetracker.backend.web.helper.ErrorCreatingHelper;
import io.jsonwebtoken.SignatureException;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Authentication filter
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public class AuthFilter implements Filter {
  private static final String AUTH_PATH = "/api/v1/auth/login";
  private static final String REGISTER_PATH = "/api/v1/users/user";

  @Inject @JwtTokenAuthServiceQualifier private AuthService authService;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    String currentAuthPath = httpServletRequest.getContextPath() + AUTH_PATH;
    String currentRegisterPath = httpServletRequest.getContextPath() + REGISTER_PATH;
    String requestURI = httpServletRequest.getRequestURI();
    String requestMethod = httpServletRequest.getMethod();

    if (requestMethod.equalsIgnoreCase("OPTIONS")
        || requestURI.equalsIgnoreCase(currentAuthPath)
        || (requestURI.equalsIgnoreCase(currentRegisterPath)
            && requestMethod.equalsIgnoreCase("POST"))) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
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
