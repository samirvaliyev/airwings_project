package com.example.epamfinalproject.Controllers.Commands.Administrator;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import com.example.epamfinalproject.Utility.SessionUtility;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.example.epamfinalproject.Database.Queries.AirplaneQueries.GET_ALL_AIRPLANES_FOR_FIRST_PAGE_QUERY;

/**
 * The Command to delete a Airplane record from the database.
 * The command changes the value of the "deleted" field to true,
 * after that the record is no longer displayed in the interface
 */
public class DeleteAirplaneCommand implements Command {
  private static final Logger log = LogManager.getLogger(DeleteAirplaneCommand.class);

  private final AirplaneService airplaneService;

  public DeleteAirplaneCommand(AirplaneService airplaneService) {
    this.airplaneService = airplaneService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);

    List<String> params = List.of(request.getParameterValues(FieldKey.ENTITY_ID));

    if (params.isEmpty()) {
      log.trace("Not a Flight has been ticked off");
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    for (String param : params) {
      if(Integer.parseInt(param) <= 0){
        log.warn("Invalid Entity ID");
        request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.PARAM_INVALID);
        return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
      }
      airplaneService.deleteAirplaneByID(Integer.parseInt(param));
      log.debug("Record with id " + Integer.parseInt(param) + "was deleted");
    }

    SessionUtility.setAirplanesParams(
        request, airplaneService.getAllAirplanesForPage(GET_ALL_AIRPLANES_FOR_FIRST_PAGE_QUERY));
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.DELETE_AIRPLANE_PAGE;
  }
}
