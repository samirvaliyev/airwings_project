package com.example.epamfinalproject.Controllers.Filters;

import com.example.epamfinalproject.Controllers.Commands.Common.ProfileCommand;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Utility.Constants;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.HashSet;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

@WebFilter(filterName = "authenticationFilter")
public class AuthenticationFilter implements Filter {
  private static final Logger log = Logger.getLogger(AuthenticationFilter.class.getName());

  @Override
  public void init(FilterConfig filterConfig) {}

  @Override
  public void destroy() {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.debug("Filter starts");
    final HttpServletRequest req = (HttpServletRequest) request;
    final User user = (User) req.getSession().getAttribute("user");
    ServletContext context = request.getServletContext();
    final String login = (String) req.getSession().getAttribute("login");
    HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");

    // If user already logged on another browser or device, refuses to logging in him twice
    if (user == null && login != null && loggedUsers.contains(login)) {

        req.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ALREADY_LOGGED);
        log.trace("Set the request attribute: message " + MessageKeys.ALREADY_LOGGED);

        request.getRequestDispatcher(Path.MAIN_PAGE).forward(request, response);
      }
    // Prevent error message from displaying after page refreshing
    if (req.getSession().getAttribute("message-displayed") != null
        && (boolean) req.getSession().getAttribute("message-displayed")) {
      req.getSession().removeAttribute(Constants.MESSAGE);
    } else {
      req.getSession().setAttribute("message-displayed", true);
    }

    // Set default locale if user has not been on site yet
    if (req.getSession().getAttribute("locale") == null) {
      req.getSession().setAttribute("locale", "EN");
    }

    log.debug("Filter finished");

    chain.doFilter(request, response);
  }
}
