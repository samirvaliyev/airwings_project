package com.example.epamfinalproject.Utility;

import com.example.epamfinalproject.Entities.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/** Utility class for validating incoming data */
public class Validation {

  private Validation() {}

  public static boolean validateUserFields(User user) {
    return Pattern.compile(Regex.USER_LOGIN).matcher(user.getLogin()).find()
        && Pattern.compile(Regex.USER_FIRSTNAME).matcher(user.getFirstName()).find()
        && Pattern.compile(Regex.USER_LASTNAME).matcher(user.getLastName()).find();
  }

  public static boolean validateStaffFields(Staff staff) {
    return Pattern.compile(Regex.USER_FIRSTNAME).matcher(staff.getFirstName()).find()
        && Pattern.compile(Regex.USER_LASTNAME).matcher(staff.getLastName()).find();
  }

  public static boolean validateFlightFields(Flight flight) {
    return Pattern.compile(Regex.FLIGHT_NAME).matcher(flight.getName()).find();
  }

  public static boolean validateRouteFields(Route route) {
    return Pattern.compile(Regex.ROUTE_DEPARTURE).matcher(route.getDeparture()).find()
        && Pattern.compile(Regex.ROUTE_DESTINATION).matcher(route.getDestination()).find();
  }

  public static boolean validateAirplaneFields(Airplane airplane) {
    return Pattern.compile(Regex.AIRPLANE_NAME).matcher(airplane.getName()).find();
  }
  /**
   * @param date a string that supposedly contains a date
   * @return true - if date is valid format, false - if parsing is impossible
   */
  public static boolean isDateValid(String date) {
    if (date.equals("")) return true;
    try {
      LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }
}
