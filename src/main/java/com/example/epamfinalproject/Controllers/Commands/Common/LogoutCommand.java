package com.example.epamfinalproject.Controllers.Commands.Common;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Utility.Constants;
import java.util.HashSet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogoutCommand implements Command {
  private static final Logger log = LogManager.getLogger(LogoutCommand.class);

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);

    HttpSession session = request.getSession(false);

    // remove current user from logged list
    ServletContext context = request.getServletContext();
    HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
    if (session != null) {
      loggedUsers.remove(((User) session.getAttribute("user")).getLogin());
      context.setAttribute("loggedUsers", loggedUsers);
      session.invalidate();
    }

    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.MAIN_PAGE;
  }
}
