package com.example.epamfinalproject.Controllers.Commands.Common;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ResetFilterCommand implements Command {
  private static final Logger log = LogManager.getLogger(ResetFilterCommand.class);
  private final AirplaneService airplaneService;

  public ResetFilterCommand(AirplaneService airplaneService) {
    this.airplaneService = airplaneService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);

    request.getSession().removeAttribute(FieldKey.AIRPLANE_LEAVING);
    request.getSession().removeAttribute(FieldKey.AIRPLANE_ARRIVING);
    request.getSession().removeAttribute(FieldKey.TRANSIT_TIME);

    CatalogueCommand command = new CatalogueCommand(airplaneService);
    command.execute(request);
    return Constants.REDIRECT + Path.CATALOGUE_PAGE;
  }
}
