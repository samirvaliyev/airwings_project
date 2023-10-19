package com.example.epamfinalproject.Controllers.Commands.Administrator;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Services.RouteService;
import com.example.epamfinalproject.Services.FlightService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import com.example.epamfinalproject.Utility.SessionUtility;
import com.example.epamfinalproject.Utility.Validation;
import java.time.LocalDate;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.example.epamfinalproject.Database.Queries.AirplaneQueries.GET_ALL_AIRPLANES_FOR_FIRST_PAGE_QUERY;

/**
 * The Command collects data from the request and checks which fields have been changed, generates a
 * new Airplane class instance and overwrites the data in the database. Fields for which there is no
 * new data remain unchanged.
 */
public class EditAirplaneCommand implements Command {
  private static final Logger log = LogManager.getLogger(EditAirplaneCommand.class);

  private final AirplaneService airplaneService;
  private final FlightService flightService;
  private final RouteService routeService;

  public EditAirplaneCommand(
          AirplaneService airplaneService, FlightService flightService, RouteService routeService) {
    this.airplaneService = airplaneService;
    this.flightService = flightService;
    this.routeService = routeService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);
    boolean flightFlag = false;
    boolean routeFlag = false;
    boolean airplaneFlag = false;

    Airplane airplane = (Airplane) request.getSession().getAttribute("airplane");

    if (!Objects.equals(request.getParameter(FieldKey.AIRPLANE_NAME), "")) {
      airplane.setName(request.getParameter(FieldKey.AIRPLANE_NAME));
      airplaneFlag = true;
    }
    if (!Objects.equals(request.getParameter("flight_name"), "")) {
      airplane.getFlight().setName(request.getParameter("flight_name"));
      flightFlag = true;
    }

    if (Integer.parseInt(request.getParameter(FieldKey.PASSENGER_CAPACITY)) != 0) {
      airplane
              .getFlight()
              .setPassengerCapacity(
                      Integer.parseInt(request.getParameter(FieldKey.PASSENGER_CAPACITY)));
      flightFlag = true;
    }

    if (!Validation.validateFlightFields(airplane.getFlight())) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.FLIGHT_INVALID);
      log.trace("Invalid Flight parameters");
      log.debug(Constants.COMMAND_FINISHED);
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }

    if (!Objects.equals(request.getParameter(FieldKey.DEPARTURE), "")) {
      airplane.getRoute().setDeparture(request.getParameter(FieldKey.DEPARTURE));
      routeFlag = true;
    }
    if (!Objects.equals(request.getParameter(FieldKey.DESTINATION), "")) {
      airplane.getRoute().setDestination(request.getParameter(FieldKey.DESTINATION));
      routeFlag = true;
    }
    if (Integer.parseInt(request.getParameter(FieldKey.TRANSIT_TIME)) != 0) {
      airplane
          .getRoute()
          .setTransitTime(Integer.parseInt(request.getParameter(FieldKey.TRANSIT_TIME)));
      routeFlag = true;
    }
    if (!Validation.validateRouteFields(airplane.getRoute())) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ROUTE_INVALID);
      log.trace("Invalid Route parameters");
      log.debug(Constants.COMMAND_FINISHED);
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    if (!Objects.equals(request.getParameter(FieldKey.AIRPLANE_LEAVING), "")
        && Validation.isDateValid((request.getParameter(FieldKey.AIRPLANE_LEAVING)))) {
      airplane.setStartOfTheAirplane(LocalDate.parse(request.getParameter(FieldKey.AIRPLANE_LEAVING)));
      airplaneFlag = true;
    }
    if (!Objects.equals(request.getParameter(FieldKey.AIRPLANE_ARRIVING), "")
        && Validation.isDateValid((request.getParameter(FieldKey.AIRPLANE_ARRIVING)))) {
      airplane.setStartOfTheAirplane(LocalDate.parse(request.getParameter(FieldKey.AIRPLANE_ARRIVING)));
      airplaneFlag = true;
    }
    if (Integer.parseInt(request.getParameter(FieldKey.AIRPLANE_PRICE)) != 0) {
      airplane.setPrice(Integer.parseInt(request.getParameter(FieldKey.AIRPLANE_PRICE)));
      airplaneFlag = true;
    }

    if (Validation.validateAirplaneFields(airplane)) {
      if(airplaneFlag) {
        airplaneService.updateAirplaneByID(airplane, airplane.getId());
        log.debug("Airplane Record was updated");
      }
      if (flightFlag) {
        flightService.updateFlightByID(airplane.getFlight(), airplane.getFlight().getId());
        log.debug("Flight Record was updated");
      }
      if (routeFlag) {
        routeService.updateRouteByID(airplane.getRoute(), airplane.getRoute().getId());
        log.debug("Route Record was updated");
      }
      SessionUtility.setAirplanesParams(
          request, airplaneService.getAllAirplanesForPage(GET_ALL_AIRPLANES_FOR_FIRST_PAGE_QUERY));

    } else {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.AIRPLANE_INVALID);
      log.trace("Invalid Airplane parameters");
      log.debug(Constants.COMMAND_FINISHED);
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.DISPLAY_EDIT_CATALOGUE_PAGE;
  }
}
