package com.example.epamfinalproject.Controllers.Commands.Common;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Utility.Constants;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import  java.util.Base64;

import com.example.epamfinalproject.Utility.FieldKey;
//import com.lambdaworks.codec.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class ProfileCommand implements Command {
  private static final Logger log = LogManager.getLogger(ProfileCommand.class);

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);

    if (request.getSession().getAttribute("user") == null) {
      log.debug("Unable to redirect to Profile page");
      log.debug(Constants.COMMAND_FINISHED);
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }
    User user = (User) request.getSession().getAttribute("user");

    if (user.getRole().equals(UserRole.ADMINISTRATOR)) {
      log.debug("Redirect to " + UserRole.ADMINISTRATOR + " page");
      log.debug(Constants.COMMAND_FINISHED);
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    log.debug("Redirect to " + UserRole.CLIENT + " page");
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.CLIENT_PAGE;
  }
}
