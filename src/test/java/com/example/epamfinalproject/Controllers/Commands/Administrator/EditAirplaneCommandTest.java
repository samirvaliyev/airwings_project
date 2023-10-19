package com.example.epamfinalproject.Controllers.Commands.Administrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.AirplaneDAO;
import com.example.epamfinalproject.Database.Interfaces.RouteDAO;
import com.example.epamfinalproject.Database.Interfaces.FlightDAO;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Route;
import com.example.epamfinalproject.Entities.Flight;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Services.RouteService;
import com.example.epamfinalproject.Services.FlightService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EditAirplaneCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;
  private static Airplane airplane;

  @BeforeAll
  static void beforeAll() {
    session = mock(HttpSession.class);
    request = mock(HttpServletRequest.class);
    AirplaneDAO airplaneDAO = mock(AirplaneDAO.class);
    FlightDAO flightDAO = mock(FlightDAO.class);
    RouteDAO routeDAO = mock(RouteDAO.class);
    airplane = new Airplane(new Flight(), new Route(), null, 0, null, null);
    command =
        new EditAirplaneCommand(
            new AirplaneService(airplaneDAO), new FlightService(flightDAO), new RouteService(routeDAO));
  }

  @ParameterizedTest
  @MethodSource("invalidData")
  void executeInvalidFlightNameTest(String name) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("airplane")).thenReturn(airplane);

    when(request.getParameter(FieldKey.AIRPLANE_NAME)).thenReturn("Name");
    when(request.getParameter("flight_name")).thenReturn(name);
    when(request.getParameter(FieldKey.PASSENGER_CAPACITY)).thenReturn("-1");

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }

  @ParameterizedTest
  @MethodSource("invalidRouteData")
  void executeInvalidRouteTest(String departure, String destination) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("airplane")).thenReturn(airplane);

    when(request.getParameter(FieldKey.AIRPLANE_NAME)).thenReturn("Name");
    when(request.getParameter("flight_name")).thenReturn("Name");
    when(request.getParameter(FieldKey.PASSENGER_CAPACITY)).thenReturn("0");

    when(request.getParameter(FieldKey.DEPARTURE)).thenReturn(departure);
    when(request.getParameter(FieldKey.DESTINATION)).thenReturn(destination);
    when(request.getParameter(FieldKey.TRANSIT_TIME)).thenReturn("0");

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }

  @ParameterizedTest
  @MethodSource("invalidData")
  void executeInvalidAirplaneTest(String name) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("airplane")).thenReturn(airplane);

    when(request.getParameter(FieldKey.AIRPLANE_NAME)).thenReturn(name);
    when(request.getParameter("flight_name")).thenReturn("Name");
    when(request.getParameter(FieldKey.PASSENGER_CAPACITY)).thenReturn("0");

    when(request.getParameter(FieldKey.DEPARTURE)).thenReturn("Departure");
    when(request.getParameter(FieldKey.DESTINATION)).thenReturn("Destination");
    when(request.getParameter(FieldKey.TRANSIT_TIME)).thenReturn("0");

    when(request.getParameter(FieldKey.AIRPLANE_LEAVING)).thenReturn("2022-10-10");
    when(request.getParameter(FieldKey.AIRPLANE_ARRIVING)).thenReturn("2022-10-10");
    when(request.getParameter(FieldKey.AIRPLANE_PRICE)).thenReturn("0");

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }

  @ParameterizedTest
  @MethodSource("invalidDateData")
  void executeInvalidAirplaneDateTest(String leavingDate, String arrivingDate) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("airplane")).thenReturn(airplane);

    when(request.getParameter(FieldKey.AIRPLANE_NAME)).thenReturn("Name");
    when(request.getParameter("flight_name")).thenReturn("Name");
    when(request.getParameter(FieldKey.PASSENGER_CAPACITY)).thenReturn("0");

    when(request.getParameter(FieldKey.DEPARTURE)).thenReturn("Departure");
    when(request.getParameter(FieldKey.DESTINATION)).thenReturn("Destination");
    when(request.getParameter(FieldKey.TRANSIT_TIME)).thenReturn("0");

    when(request.getParameter(FieldKey.AIRPLANE_LEAVING)).thenReturn(leavingDate);
    when(request.getParameter(FieldKey.AIRPLANE_ARRIVING)).thenReturn(arrivingDate);
    when(request.getParameter(FieldKey.AIRPLANE_PRICE)).thenReturn("0");

    assertEquals(Constants.REDIRECT + Path.DISPLAY_EDIT_CATALOGUE_PAGE, command.execute(request));
  }

  @ParameterizedTest
  @MethodSource("validData")
  void executeValidAirplaneTest(String name) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("airplane")).thenReturn(airplane);

    when(request.getParameter(FieldKey.AIRPLANE_NAME)).thenReturn(name);
    when(request.getParameter("flight_name")).thenReturn(name);
    when(request.getParameter(FieldKey.PASSENGER_CAPACITY)).thenReturn("1");
    when(request.getParameter(FieldKey.DEPARTURE)).thenReturn(name);
    when(request.getParameter(FieldKey.DESTINATION)).thenReturn(name);
    when(request.getParameter(FieldKey.TRANSIT_TIME)).thenReturn("1");
    when(request.getParameter(FieldKey.AIRPLANE_LEAVING)).thenReturn("2022-10-10");
    when(request.getParameter(FieldKey.AIRPLANE_ARRIVING)).thenReturn("2022-10-10");
    when(request.getParameter(FieldKey.AIRPLANE_PRICE)).thenReturn("1");

    assertEquals(Constants.REDIRECT + Path.DISPLAY_EDIT_CATALOGUE_PAGE, command.execute(request));
  }

  static Stream<Arguments> invalidData() {
    return Stream.of(
        Arguments.of("Invalid_value"),
        Arguments.of("invalidValue"),
        Arguments.of("invalid value"),
        Arguments.of("invalid Value"),
        Arguments.of(" "),
        Arguments.of("Invalid=Vvalue"),
        Arguments.of(" Invalid2232value"));
  }

  static Stream<Arguments> validData() {
    return Stream.of(
        Arguments.of("Valid-value"),
        Arguments.of("Valid Value"),
        Arguments.of("Valid"),
        Arguments.of("Very valid Value"),
        Arguments.of("Very-valid-Value"));
  }

  static Stream<Arguments> invalidDateData() {
    return Stream.of(
        Arguments.of("202-10-10", "2002-10-10"),
        Arguments.of("2002-10-10", "2020-20-20"),
        Arguments.of("0", "-34-34-1222"),
        Arguments.of("12-22", "55-55-5555"),
        Arguments.of("2023-2-30", "2020-20-20"));
  }

  static Stream<Arguments> invalidRouteData() {
    return Stream.of(
        Arguments.of("Valid-value", "Invalid_value"),
        Arguments.of("invalidValue", "Valid Value"),
        Arguments.of("invalid value", " "),
        Arguments.of("invalid Value", "Invalid=Vvalue"),
        Arguments.of(" ", "Valid"),
        Arguments.of("Very valid Value", "Invalid=Vvalue"),
        Arguments.of("Invalid2232value", "Very valid Value"));
  }
}
