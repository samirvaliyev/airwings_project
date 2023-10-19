package com.example.epamfinalproject.Utility;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryBuilderTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static QueryBuilder builder;
  private static String airplaneQuery;
  private static String countQuery;

  @BeforeAll
  static void beforeAll() {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    builder = new QueryBuilder();
    airplaneQuery =
        "select * from airplanes "
            + "inner join routes r on r.id = airplanes.route_id"
            + " inner join flights s on s.id = airplanes.flight_id "
            + " where deleted = false";
    countQuery =
        "select count(airplanes.id) from airplanes inner join routes r on r.id = airplanes.route_id"
            + " where deleted = false";
  }

  @ParameterizedTest
  @MethodSource("filterAirplaneValues")
  void airplaneQueryBuilderTest(
      String leavingDate, String arrivingDate, String transitTime, String expectedResult) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute(FieldKey.AIRPLANE_LEAVING)).thenReturn(leavingDate);
    when(request.getSession().getAttribute(FieldKey.AIRPLANE_ARRIVING)).thenReturn(arrivingDate);
    when(request.getSession().getAttribute(FieldKey.TRANSIT_TIME)).thenReturn(transitTime);

    assertEquals(builder.airplaneQueryBuilder(request, 1, null), expectedResult);
  }

  @ParameterizedTest
  @MethodSource("filterCountValues")
  void airplaneCountQueryBuilderTest(
      String leavingDate, String arrivingDate, String transitTime, String expectedResult) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute(FieldKey.AIRPLANE_LEAVING)).thenReturn(leavingDate);
    when(request.getSession().getAttribute(FieldKey.AIRPLANE_ARRIVING)).thenReturn(arrivingDate);
    when(request.getSession().getAttribute(FieldKey.TRANSIT_TIME)).thenReturn(transitTime);

    assertEquals(builder.airplaneCountQueryBuilder(request, null), expectedResult);
  }

  @ParameterizedTest
  @MethodSource("filterAirplaneAdminValues")
  void airplaneQueryBuilderAdminTest(
      String leavingDate, String arrivingDate, String transitTime, String expectedResult) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute(FieldKey.AIRPLANE_LEAVING)).thenReturn(leavingDate);
    when(request.getSession().getAttribute(FieldKey.AIRPLANE_ARRIVING)).thenReturn(arrivingDate);
    when(request.getSession().getAttribute(FieldKey.TRANSIT_TIME)).thenReturn(transitTime);

    assertEquals(
        builder.airplaneQueryBuilder(
            request, 1, new User.UserBuilder().role(UserRole.ADMINISTRATOR).build()),
        expectedResult);
  }

  @ParameterizedTest
  @MethodSource("filterCountAdminValues")
  void airplaneCountQueryBuilderAdminTest(
      String leavingDate, String arrivingDate, String transitTime, String expectedResult) {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute(FieldKey.AIRPLANE_LEAVING)).thenReturn(leavingDate);
    when(request.getSession().getAttribute(FieldKey.AIRPLANE_ARRIVING)).thenReturn(arrivingDate);
    when(request.getSession().getAttribute(FieldKey.TRANSIT_TIME)).thenReturn(transitTime);

    assertEquals(builder.airplaneCountQueryBuilder(request,
            new User.UserBuilder().role(UserRole.ADMINISTRATOR).build()), expectedResult);
  }

  Stream<Arguments> filterAirplaneValues() {
    return Stream.of(
        Arguments.of(
            "2022-10-10",
            null,
            null,
            airplaneQuery + " and start_date >= '2022-10-10' limit 6 offset 0"),
        Arguments.of(
            "",
            "2022-10-13",
            "",
            airplaneQuery + " and start_date >= now() and end_date <= '2022-10-13' limit 6 offset 0"),
        Arguments.of(
            "",
            "",
            "3",
            airplaneQuery + " and start_date >= now() and r.transit_time >= 3 limit 6 offset 0"),
        Arguments.of(
            "2022-10-12",
            "2022-10-15",
            "",
            airplaneQuery
                + " and start_date >= '2022-10-12' and end_date <= '2022-10-15' limit 6 offset 0"),
        Arguments.of(
            "2022-10-25",
            "2022-10-13",
            "4",
            airplaneQuery
                + " and start_date >= '2022-10-25' and end_date <= '2022-10-13' and"
                + " r.transit_time >= 4 limit 6 offset 0"),
        // Invalid data arguments
        Arguments.of(
            "2022-40-10", null, null, airplaneQuery + " and start_date >= now() limit 6 offset 0"),
        Arguments.of(
            "", "202-10-13", "", airplaneQuery + " and start_date >= now() limit 6 offset 0"),
        Arguments.of("", "", "-3", airplaneQuery + " and start_date >= now() limit 6 offset 0"),
        Arguments.of(
            "2022-10-12",
            "2022-10-15",
            "",
            airplaneQuery
                + " and start_date >= '2022-10-12' and end_date <= '2022-10-15' limit 6 offset 0"),
        Arguments.of(
            "200022-10-25",
            "2022-1-0-13",
            "4",
            airplaneQuery
                + " and start_date >= now() and"
                + " r.transit_time >= 4 limit 6 offset 0"));
  }

  Stream<Arguments> filterCountValues() {
    return Stream.of(
        Arguments.of("2022-10-10", null, null, countQuery + " and start_date >= '2022-10-10'"),
        Arguments.of(
            "",
            "2022-10-13",
            "",
            countQuery + " and start_date >= now() and end_date <= '2022-10-13'"),
        Arguments.of("", "", "3", countQuery + " and start_date >= now() and r.transit_time >= 3"),
        Arguments.of(
            "2022-10-12",
            "2022-10-15",
            "",
            countQuery + " and start_date >= '2022-10-12' and end_date <= '2022-10-15'"),
        Arguments.of(
            "2022-10-25",
            "2022-10-13",
            "4",
            countQuery
                + " and start_date >= '2022-10-25' and end_date <= '2022-10-13' and"
                + " r.transit_time >= 4"),
        // Invalid data arguments
        Arguments.of("2022-40-10", null, null, countQuery + " and start_date >= now()"),
        Arguments.of("", "202-10-13", "", countQuery + " and start_date >= now()"),
        Arguments.of("", "", "-3", countQuery + " and start_date >= now()"),
        Arguments.of(
            "2022-10-12",
            "2022-10-15",
            "",
            countQuery + " and start_date >= '2022-10-12' and end_date <= '2022-10-15'"),
        Arguments.of(
            "200022-10-25",
            "2022-1-0-13",
            "4",
            countQuery + " and start_date >= now() and" + " r.transit_time >= 4"));
  }

  Stream<Arguments> filterAirplaneAdminValues() {
    return Stream.of(
        Arguments.of(
            "2022-10-10",
            null,
            null,
            airplaneQuery + " and start_date >= '2022-10-10' and confirmed = false limit 6 offset 0"),
        Arguments.of(
            "",
            "2022-10-13",
            "",
            airplaneQuery
                + " and confirmed = false and end_date <= '2022-10-13'"
                + " limit 6 offset 0"),
        Arguments.of(
            "",
            "",
            "3",
            airplaneQuery
                + " and confirmed = false and r.transit_time >= 3 limit 6"
                + " offset 0"),
        Arguments.of(
            "2022-10-12",
            "2022-10-15",
            "",
            airplaneQuery
                + " and start_date >= '2022-10-12' and confirmed = false and end_date <="
                + " '2022-10-15' limit 6 offset 0"),
        Arguments.of(
            "2022-10-25",
            "2022-10-13",
            "4",
            airplaneQuery
                + " and start_date >= '2022-10-25' and confirmed = false and end_date <="
                + " '2022-10-13' and r.transit_time >= 4 limit 6 offset 0"),
        // Invalid data arguments
        Arguments.of(
            "2022-40-10",
            null,
            null,
            airplaneQuery + " and confirmed = false limit 6 offset 0"),
        Arguments.of(
            "",
            "202-10-13",
            "",
            airplaneQuery + " and confirmed = false limit 6 offset 0"),
        Arguments.of(
            "",
            "",
            "-3",
            airplaneQuery + " and confirmed = false limit 6 offset 0"),
        Arguments.of(
            "2022-10-12",
            "2022-10-15",
            "",
            airplaneQuery
                + " and start_date >= '2022-10-12' and confirmed = false and end_date <="
                + " '2022-10-15' limit 6 offset 0"),
        Arguments.of(
            "200022-10-25",
            "2022-1-0-13",
            "4",
            airplaneQuery
                + " and confirmed = false and"
                + " r.transit_time >= 4 limit 6 offset 0"));
  }

  Stream<Arguments> filterCountAdminValues() {
    return Stream.of(
        Arguments.of(
            "2022-10-10",
            null,
            null,
            countQuery + " and start_date >= '2022-10-10' and confirmed = false"),
        Arguments.of(
            "",
            "2022-10-13",
            "",
            countQuery + " and confirmed = false and end_date <= '2022-10-13'"),
        Arguments.of(
            "",
            "",
            "3",
            countQuery + " and confirmed = false and r.transit_time >= 3"),
        Arguments.of(
            "2022-10-12",
            "2022-10-15",
            "",
            countQuery
                + " and start_date >= '2022-10-12' and confirmed = false and end_date <="
                + " '2022-10-15'"),
        Arguments.of(
            "2022-10-25",
            "2022-10-13",
            "4",
            countQuery
                + " and start_date >= '2022-10-25' and confirmed = false and end_date <="
                + " '2022-10-13' and r.transit_time >= 4"),
        // Invalid data arguments
        Arguments.of("2022-40-10", null, null, countQuery + " and confirmed = false"),
        Arguments.of("", "202-10-13", "", countQuery + " and confirmed = false"),
        Arguments.of("", "", "-3", countQuery + " and confirmed = false"),
        Arguments.of(
            "2022-10-12",
            "2022-10-15",
            "",
            countQuery
                + " and start_date >= '2022-10-12' and confirmed = false and end_date <="
                + " '2022-10-15'"),
        Arguments.of(
            "200022-10-25",
            "2022-1-0-13",
            "4",
            countQuery
                + " and confirmed = false and"
                + " r.transit_time >= 4"));
  }
}
