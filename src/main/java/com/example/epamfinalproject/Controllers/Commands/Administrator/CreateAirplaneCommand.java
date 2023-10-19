package com.example.epamfinalproject.Controllers.Commands.Administrator;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Flight;
import com.example.epamfinalproject.Entities.Route;
import com.example.epamfinalproject.Entities.Staff;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Services.RouteService;
import com.example.epamfinalproject.Services.FlightService;
import com.example.epamfinalproject.Services.StaffService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;

import com.example.epamfinalproject.Utility.Validation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A command to create an instance of the Airplane class and add a record to the database. Available
 * for Administrator
 */
public class CreateAirplaneCommand implements Command {
  private static final Logger log = LogManager.getLogger(CreateAirplaneCommand.class);
  private final AirplaneService airplaneService;
  private final RouteService routeService;
  private final FlightService flightService;
  private final StaffService staffService;

  public CreateAirplaneCommand(
      AirplaneService airplaneService,
      RouteService routeService,
      FlightService flightService,
      StaffService staffService) {
    this.airplaneService = airplaneService;
    this.routeService = routeService;
    this.flightService = flightService;
    this.staffService = staffService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);

    Flight flight = createFlight(request);
    if (flight == null) {
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    Route route = createRoute(request);
    if (route == null) {
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    List<Staff> staff = createStaffList(request);
    if (staff.isEmpty() || staff.size() < 3) {
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    Airplane airplane = createAirplane(request, flight, route);
    if (airplane != null) {
      airplaneService.createAirplane(airplane);
      addStaffRecords(staff, flightService.getFlightByName(flight.getName()).getId());
      log.debug("Record was added");
      log.debug(Constants.COMMAND_FINISHED);
    } else {
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    return Constants.REDIRECT + Path.CREATE_AIRPLANE_PAGE;
  }

  /**
   * Shapes request to Route instance
   *
   * @return Route instance
   */
  private Route createRoute(HttpServletRequest request) {
    Route route = new Route();
    route.setDeparture(request.getParameter(FieldKey.DEPARTURE));
    route.setDestination(request.getParameter(FieldKey.DESTINATION));
    route.setTransitTime(Integer.parseInt(request.getParameter(FieldKey.TRANSIT_TIME)));
    if (!Validation.validateRouteFields(route)
        || routeService.getRouteByAllParameters(route).getDeparture() != null) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ROUTE_INVALID);
      log.trace("Invalid Route parameters");
      log.debug(Constants.COMMAND_FINISHED);
      return null;
    }
    return route;
  }
  /**
   * Shapes request to Flight instance
   *
   * @return new Flight instance
   */
  private Flight createFlight(HttpServletRequest request) {
    Flight flight = new Flight();
    flight.setName(request.getParameter(FieldKey.AIRPLANE_FLIGHT_NAME));
    flight.setPassengerCapacity(Integer.parseInt(request.getParameter(FieldKey.PASSENGER_CAPACITY)));
    if (!Validation.validateFlightFields(flight)
        || flightService.getFlightByName(flight.getName()).getName() != null) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.FLIGHT_INVALID);
      log.trace("Invalid Flight parameters");
      log.debug(Constants.COMMAND_FINISHED);
      return null;
    }
    return flight;
  }
  /**
   * Shapes request to Route instance
   *
   * @return Route instance
   */
  private Airplane createAirplane(HttpServletRequest request, Flight flight, Route route) {
    Airplane airplane = new Airplane();
    airplane.setName(request.getParameter(FieldKey.AIRPLANE_NAME));
    airplane.setPrice(Integer.parseInt(request.getParameter(FieldKey.AIRPLANE_PRICE)));
    airplane.setStartOfTheAirplane(
        LocalDate.parse(String.valueOf(request.getParameter(FieldKey.AIRPLANE_LEAVING))));
    airplane.setEndOfTheAirplane(
        LocalDate.parse(String.valueOf(request.getParameter(FieldKey.AIRPLANE_ARRIVING))));
    airplane.setFlight(flight);
    airplane.setRoute(route);
    if (!Validation.validateAirplaneFields(airplane)) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.AIRPLANE_INVALID);
      log.trace("Invalid Airplane parameters");
      log.debug(Constants.COMMAND_FINISHED);
      return null;
    }
    return airplane;
  }
  /**
   * Shape request into list of Staff entities
   *
   * @return list of Staff entities
   */
  private List<Staff> createStaffList(HttpServletRequest request) {
    List<Staff> staffList = new ArrayList<>();
    for (int i = 1; i <= Constants.STAFF_NUMBER; i++) {
      Staff staff = new Staff();
      staff.setFirstName(request.getParameter(FieldKey.FIRST_NAME + i));
      staff.setLastName(request.getParameter(FieldKey.LAST_NAME + i));

      if (!Validation.validateStaffFields(staff)) {
        request.getSession().setAttribute("message", MessageKeys.STAFF_INVALID);
        log.trace("Invalid Airplane parameters");
        log.debug(Constants.COMMAND_FINISHED);
        return Collections.emptyList();
      }
      staffList.add(staff);
    }
    return staffList;
  }

  /**
   * @param staff List of new Staff instances
   * @param flightId ID of flight
   */
  private void addStaffRecords(List<Staff> staff, long flightId) {
    for (Staff value : staff) {
      value.setFlightId(flightId);
      staffService.registerStaff(value);
      log.debug("Staff record was added");
    }
  }
}
