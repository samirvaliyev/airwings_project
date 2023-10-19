package com.example.epamfinalproject.Controllers.Commands.Common;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Utility.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CatalogueCommand implements Command {
  private static final Logger log = LogManager.getLogger(CatalogueCommand.class);
  private final AirplaneService airplaneService;
  private static final QueryBuilder builder = new QueryBuilder();

  public CatalogueCommand(AirplaneService airplaneService) {
    this.airplaneService = airplaneService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);

    User user = (User) request.getSession().getAttribute("user");

    int page = 1;
    String pagePath = request.getParameter("page-path");

    if (request.getParameter("page") != null) {
      page = Integer.parseInt(request.getParameter("page"));
    }
    if (page < 1 || pagePath == null) {
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }
    List<Airplane> airplanes;
    checkFilter(request);
    String airplaneQuery = builder.airplaneQueryBuilder(request, page, user);

      airplanes = airplaneService.getAllAirplanesForPage(airplaneQuery);

    SessionUtility.setAirplanesParams(request, Sort.sortAirplanesByStartDate(airplanes));

    int recordsCount =
        airplaneService.getNumberOfActualAirplanes(builder.airplaneCountQueryBuilder(request, user));
    int pageCount = (int) Math.ceil(recordsCount * 1.0 / Constants.PAGE_SIZE);
    request.getSession().setAttribute("numOfPages", pageCount);
    request.getSession().setAttribute("currentPage", page);

    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + pagePath;
  }


  private void checkFilter(HttpServletRequest request) {
    Object leavingDate = request.getParameter(FieldKey.AIRPLANE_LEAVING);
    if (leavingDate != null
        && leavingDate != ""
        && Validation.isDateValid(String.valueOf(leavingDate))) {
      request.getSession().setAttribute(FieldKey.AIRPLANE_LEAVING, leavingDate);
    }
    Object arrivingDate = request.getParameter(FieldKey.AIRPLANE_ARRIVING);
    if (arrivingDate != null
        && !"".equals(arrivingDate)
        && Validation.isDateValid(String.valueOf(arrivingDate))) {
      request.getSession().setAttribute(FieldKey.AIRPLANE_ARRIVING, arrivingDate);
    }

    Object transitTime = request.getParameter(FieldKey.TRANSIT_TIME);
    if (transitTime != null
        && !"".equals(transitTime)
        && Integer.parseInt(String.valueOf(transitTime)) > 0) {
      request.getSession().setAttribute(FieldKey.TRANSIT_TIME, transitTime);
    }
  }
}
