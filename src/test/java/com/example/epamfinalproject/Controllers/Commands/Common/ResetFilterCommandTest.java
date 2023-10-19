package com.example.epamfinalproject.Controllers.Commands.Common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.AirplaneDAO;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ResetFilterCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;

  @BeforeAll
  static void beforeAll() {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    command = new ResetFilterCommand(new AirplaneService(mock(AirplaneDAO.class)));
  }

  @ParameterizedTest
  @MethodSource("validFilterData")
  void executeValidTest(String leavingDate, String arrivingDate, String transitTime) {
    session.setAttribute(FieldKey.AIRPLANE_LEAVING, leavingDate);
    session.setAttribute(FieldKey.AIRPLANE_ARRIVING, arrivingDate);
    session.setAttribute(FieldKey.TRANSIT_TIME, transitTime);
    when(request.getSession()).thenReturn(session);

    assertEquals(Constants.REDIRECT + Path.CATALOGUE_PAGE, command.execute(request));
  }

  @ParameterizedTest
  @MethodSource("invalidFilterData")
  void executeInvalidTest(String leavingDate, String arrivingDate, String transitTime) {
    session.setAttribute(FieldKey.AIRPLANE_LEAVING, leavingDate);
    session.setAttribute(FieldKey.AIRPLANE_ARRIVING, arrivingDate);
    session.setAttribute(FieldKey.TRANSIT_TIME, transitTime);
    when(request.getSession()).thenReturn(session);

    assertEquals(Constants.REDIRECT + Path.CATALOGUE_PAGE, command.execute(request));
  }

  static Stream<Arguments> validFilterData() {
    return Stream.of(
        Arguments.of(null, null, "2"),
        Arguments.of(null, "2022-12-12", null),
        Arguments.of("2022-12-12", null, null),
        Arguments.of("2022-12-12", "2022-12-12", null),
        Arguments.of("2022-12-12", null, "2"),
        Arguments.of(null, "2022-12-12", "2"),
        Arguments.of("2022-12-12", "2022-12-12", "2"));
  }

  static Stream<Arguments> invalidFilterData() {
    return Stream.of(
        Arguments.of("2022-11-11", "2022-11-11", ""), // invalid transit time
        Arguments.of("2022-11-11", "202-12-12", "1"), // invalid arriving date
        Arguments.of("", "2022-11-11", "1"), // invalid leaving time
        Arguments.of("2022-12-100", "20-22-12-12", "1"), // invalid arriving and leaving dates
        Arguments.of("2022-2-2", "2022-11-11", "-2"), // invalid leaving date and transit time
        Arguments.of("2022-11-11", "", "-2"), // invalid arriving and transit time
        Arguments.of("2-0-2-2-12-12", "abvgt", "-2")); // invalid all values
  }
}
