package com.example.epamfinalproject.Controllers.Commands.Common;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Utility.Constants;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ChangeLocaleCommand implements Command {
  private static final Logger log = LogManager.getLogger(ChangeLocaleCommand.class);

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);

    request
        .getSession()
        .setAttribute("locale", request.getParameter("locale").equals("en") ? "en" : "ua");
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + request.getParameter("page-path");
  }
}
