package com.example.epamfinalproject.Controllers.Commands.Administrator;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Queries.AirplaneQueries;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import com.example.epamfinalproject.Utility.SessionUtility;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConfirmAirplaneCommand implements Command {
  private static final Logger log = LogManager.getLogger(ConfirmAirplaneCommand.class);

  private final AirplaneService airplaneService;

  public ConfirmAirplaneCommand(AirplaneService airplaneService) {
    this.airplaneService = airplaneService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);
    String param = request.getParameter(FieldKey.ENTITY_ID);

    if (param.isEmpty()) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.AIRPLANE_CONFIRMATION_ERROR);
      log.trace("Not an Airplane has been ticked off");
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    if (Long.parseLong(param) < 1) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.AIRPLANE_CONFIRMATION_ERROR);
      log.trace("Airplane ID is invalid");
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    } else {
      airplaneService.confirmAirplaneByID(Long.parseLong(param));
      log.debug("Record with id " + Integer.parseInt(param) + "was confirmed");
    }

    SessionUtility.setAirplanesParams(
        request,
        airplaneService.getAllAirplanesForPage(
                AirplaneQueries.GET_ALL_ACTUAL_AIRPLANES_FOR_FIRST_PAGE_ADMIN_QUERY));
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.CATALOGUE_PAGE;
  }
}
