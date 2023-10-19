package com.example.epamfinalproject.Utility;

import static org.junit.jupiter.api.Assertions.*;

import com.example.epamfinalproject.Entities.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ValidationTest {

  @ParameterizedTest
  @MethodSource("validUserData")
  void validateUserFieldsTrueTest(String login, String firstName, String lastName) {
    assertTrue(
        Validation.validateUserFields(
            new User.UserBuilder().login(login).firstName(firstName).lastName(lastName).build()));
  }

  @ParameterizedTest
  @MethodSource("invalidUserData")
  void validateUserFieldsFalseTest(String login, String firstName, String lastName) {
    assertFalse(
        Validation.validateUserFields(
            new User.UserBuilder().login(login).firstName(firstName).lastName(lastName).build()));
  }

  @Test
  void validateStaffTrueFields() {
    assertTrue(Validation.validateStaffFields(new Staff("Firstname", "Lastname", 1)));
  }

  @ParameterizedTest
  @MethodSource("invalidStaffData")
  void validateStaffFalseFields(String firstname, String lastname) {
    assertFalse(Validation.validateStaffFields(new Staff(firstname, lastname, 1)));
  }

  @ParameterizedTest
  @MethodSource("validRouteData")
  void validateRouteTrueFields(String departure, String destination) {
    assertTrue(Validation.validateRouteFields(new Route(departure, destination, 10)));
  }

  @ParameterizedTest
  @MethodSource("invalidRouteData")
  void validateRouteFalseFields(String departure, String destination) {
    assertFalse(Validation.validateRouteFields(new Route(departure, destination, 10)));
  }

  @ParameterizedTest
  @MethodSource("validAirplaneData")
  void validateAirplaneTrueFields(String name) {
    assertTrue(
        Validation.validateAirplaneFields(new Airplane(new Flight(), new Route(), name, 0, null, null)));
  }

  @ParameterizedTest
  @MethodSource("invalidAirplaneData")
  void validateAirplaneFalseFields(String name) {
    assertFalse(
        Validation.validateAirplaneFields(new Airplane(new Flight(), new Route(), name, 0, null, null)));
  }

  @ParameterizedTest
  @MethodSource("validDateData")
  void isDateValidTrueTest(String date) {
    assertTrue(Validation.isDateValid(date));
  }

  @ParameterizedTest
  @MethodSource("invalidDateData")
  void isDateValidFalseTest(String date) {
    assertFalse(Validation.isDateValid(date));
  }

  static Stream<Arguments> validDateData() {
    return Stream.of(
        Arguments.of("2002-12-12"), Arguments.of("2033-12-30"), Arguments.of("1000-10-11"));
  }

  static Stream<Arguments> invalidDateData() {
    return Stream.of(
        Arguments.of("202-10-10"),
        Arguments.of("2020-20-20"),
        Arguments.of("0"),
        Arguments.of("-34-34-1222"),
        Arguments.of("12-22"),
        Arguments.of("55-55-5555"),
        Arguments.of("2023-2-30"));
  }

  static Stream<Arguments> validUserData() {
    return Stream.of(
        Arguments.of("Login", "Firstname", "Lastname"),
        Arguments.of("login", "Firstname", "Lastname"),
        Arguments.of("Login123", "Firstname", "Lastname"),
        Arguments.of("Log_in", "Firstname", "Lastname"));
  }

  static Stream<Arguments> invalidUserData() {
    return Stream.of(
        Arguments.of("Lo", "Firstname", "Lastname"),
        Arguments.of("Login", "firstname", "Lastname"),
        Arguments.of("Login", "Firstname", "lastname"),
        Arguments.of("Login", "First-name", "Lastname"),
        Arguments.of("Login", "Firstname", "Last_name"),
        Arguments.of("Log*in", "Firstname", "Last Name"),
        Arguments.of("Log^in", "Firstname", "Last Name"),
        Arguments.of("Log&in", "Firstname", "Last Name"),
        Arguments.of("Log?in", "First^name", "Lastname"),
        Arguments.of("Log!in", "First^name", "L@stname"),
        Arguments.of("L", "Firstname", "Lastname"),
        Arguments.of("Login", "F", "Lastname"),
        Arguments.of("Login", "Firstname", "L"));
  }

  static Stream<Arguments> invalidStaffData() {
    return Stream.of(
        Arguments.of("firstname", "Lastname"),
        Arguments.of("Firstname", "lastname"),
        Arguments.of("First-name", "Lastname"),
        Arguments.of("Firstname", "Last_name"),
        Arguments.of("Firstname", "Last Name"),
        Arguments.of("First^name", "Lastname"),
        Arguments.of("First^name", "L@stname"),
        Arguments.of("F", "Lastname"),
        Arguments.of("Firstname", "L"));
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

  static Stream<Arguments> validRouteData() {
    return Stream.of(
        Arguments.of("Valid-value", "Valid Value"),
        Arguments.of("Very-valid-Value", "Valid Value"),
        Arguments.of("Valid", "Very valid Value"));
  }

  static Stream<Arguments> invalidAirplaneData() {
    return Stream.of(
        Arguments.of("Invalid_value"),
        Arguments.of("invalidValue"),
        Arguments.of("invalid value"),
        Arguments.of("invalid Value"),
        Arguments.of(" "),
        Arguments.of("Invalid=Vvalue"),
        Arguments.of(" Invalid2232value"));
  }

  static Stream<Arguments> validAirplaneData() {
    return Stream.of(
        Arguments.of("Valid-value"),
        Arguments.of("Valid Value"),
        Arguments.of("Valid"),
        Arguments.of("Very valid Value"),
        Arguments.of("Very-valid-Value"));
  }
}
