package com.example.epamfinalproject.Controllers.Commands.Common;

import static com.example.epamfinalproject.Database.Queries.AirplaneQueries.GET_ALL_ACTUAL_AIRPLANES_FOR_FIRST_PAGE_QUERY;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.*;
import com.example.epamfinalproject.Utility.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LoginCommand implements Command {
  private static final Logger log = LogManager.getLogger(LoginCommand.class);

  private final UserService userService;
  private final AirplaneService airplaneService;
  private final OrderService orderService;
  private final FlightService flightService;
  private final RouteService routeService;

  public LoginCommand(
      UserService userService,
      AirplaneService airplaneService,
      OrderService orderService,
      FlightService flightService,
      RouteService routeService) {
    this.userService = userService;
    this.airplaneService = airplaneService;
    this.orderService = orderService;
    this.flightService = flightService;
    this.routeService = routeService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);

    String login = request.getParameter(FieldKey.LOGIN);

    if (login == null || login.equals("")) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.LOGIN_INVALID);
      log.trace("Invalid User parameters");
      return Constants.REDIRECT + Path.LOGIN_PAGE;
    }

    User user = userService.getUserByLogin(login);

    if (validateUserData(user, request)) {
      request.getSession().setAttribute("role", user.getRole().toString());

      if (user.getRole().equals(UserRole.ADMINISTRATOR)) {

        SessionUtility.setParamsForAdmin(
            request, user, userService.getClientUsers(), orderService.getAllUnconfirmedOrders());

        log.debug("Logging in as ADMINISTRATOR");
        log.debug(Constants.COMMAND_FINISHED);
        return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
      } else {
        log.debug("Logging in as CLIENT");
        log.debug(Constants.COMMAND_FINISHED);

        SessionUtility.setParamsForClient(
            request,
            user,
            airplaneService.getAllAirplanesForPage(GET_ALL_ACTUAL_AIRPLANES_FOR_FIRST_PAGE_QUERY),
            Sort.sortOrderByStatus(orderService.getOrdersByUserID(user.getId())));
        return Constants.REDIRECT + Path.CLIENT_PAGE;
      }
    } else {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.LOGIN_INVALID);
      log.trace("Invalid User parameters");
      return Constants.REDIRECT + Path.LOGIN_PAGE;
    }
  }

  /**
   * @param user User to be authorized
   * @return true - if the user entered the correct login and password, false - if there is a
   *     mismatch
   */
  private boolean validateUserData(User user, HttpServletRequest request) {
    return (user != null)
        && (Encryptor.check(user.getPassword(), request.getParameter(FieldKey.PASSWORD)));
  }
}
