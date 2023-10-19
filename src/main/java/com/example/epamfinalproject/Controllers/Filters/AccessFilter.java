package com.example.epamfinalproject.Controllers.Filters;

import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebFilter(filterName = "accessFilter")
public class AccessFilter implements Filter {
  private static final Logger log = LogManager.getLogger(AccessFilter.class);

  // commands access
  private static final Map<String, List<String>> accessMap = new HashMap<>();
  private static List<String> commons = new ArrayList<>();

  @Override
  public void init(FilterConfig filterConfig) {

    accessMap.put(
        "administrator",
        Arrays.asList(
            "profile",
            "createAirplane",
            "displayFormWithAirplaneInfo",
            "editAirplane",
            "deleteAirplane",
            "confirmOrder",
            "confirmAll",
            "confirmAirplane"));

    accessMap.put("client", Arrays.asList("createOrder", "displayFormWithAirplaneInfo", "profile","payForTheOrder"));

    commons =
        Arrays.asList("login", "signUp", "logout", "changeLocale", "catalogue", "resetFilter");
  }

  @Override
  public void destroy() {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.debug("Filter starts");

    if (accessAllowed(request)) {
      log.debug("Filter finished");
      chain.doFilter(request, response);
    } else {
      String errorMessage = MessageKeys.ACCESS_DENIED;

      HttpServletRequest req = (HttpServletRequest) request;
      req.getSession().setAttribute("message", errorMessage);
      log.trace("Set the request attribute: message: " + errorMessage);

      if (req.getSession().getAttribute("user") == null) {

        // Set preCommand attribute to track which page to redirect to after
        // authorization
        req.getSession().setAttribute("preCommand", request.getParameter("command"));

        request.getRequestDispatcher(Path.LOGIN_PAGE).forward(request, response);
      }
      request.getRequestDispatcher(Path.MAIN_PAGE).forward(request, response);
    }
  }

  private boolean accessAllowed(ServletRequest request) {
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    String commandName = request.getParameter("command");
    if (commandName == null) return true;

    HttpSession session = httpRequest.getSession(false);
    if (session == null) {
      return false;
    }
    if (commons.contains(commandName)) {
      return true;
    }
    if (session.getAttribute("user") == null) {
      return false;
    }

    String userRole = String.valueOf(session.getAttribute("role"));
    if (userRole == null) {
      return false;
    }

    return accessMap.get(userRole).contains(commandName);
  }
}
