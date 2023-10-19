package com.example.epamfinalproject.Controllers.Commands.Common;

import static com.example.epamfinalproject.Database.Queries.AirplaneQueries.GET_ALL_ACTUAL_AIRPLANES_FOR_FIRST_PAGE_QUERY;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Services.UserService;
import com.example.epamfinalproject.Utility.*;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SignUpCommand implements Command {
  private static final Logger log = LogManager.getLogger(SignUpCommand.class);
  private final UserService userService;
  private final AirplaneService airplaneService;

  public SignUpCommand(UserService userService, AirplaneService airplaneService) {
    this.userService = userService;
    this.airplaneService = airplaneService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);
    String password = request.getParameter(FieldKey.PASSWORD);
    String confirmPassword = request.getParameter(FieldKey.CONFIRM_PASSWORD);

    if (!password.equals(confirmPassword)) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.SIGN_UP_CONFIRMATION_FAILED);
      log.trace("Password Confirmation Failed");
      return Constants.REDIRECT + Path.SIGN_UP_PAGE;
    }
    User user =
        new User.UserBuilder()
            .firstName(request.getParameter(FieldKey.FIRST_NAME))
            .lastName(request.getParameter(FieldKey.LAST_NAME))
            .login(request.getParameter(FieldKey.LOGIN))
            .password(Encryptor.encrypt(password))
            .role(UserRole.CLIENT)
            .build();

    if (!Validation.validateUserFields(user)) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.SIGN_UP_INVALID);
      log.trace("Invalid User Parameters");
      return Constants.REDIRECT + Path.SIGN_UP_PAGE;
    }
    if (userService.getUserByLogin(user.getLogin()) != null) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.SIGN_UP_EXISTS);
      log.trace("User already exists");
      return Constants.REDIRECT + Path.LOGIN_PAGE;
    }
    userService.registerUser(user);
    SessionUtility.setParamsForClient(
        request,
        user,
        airplaneService.getAllAirplanesForPage(GET_ALL_ACTUAL_AIRPLANES_FOR_FIRST_PAGE_QUERY),
        new ArrayList<>());
    request.getSession().setAttribute("role", user.getRole());

    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.CLIENT_PAGE;
  }
}
