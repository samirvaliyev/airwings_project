package com.example.epamfinalproject.Controllers.Commands.Common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.*;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.*;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;

import java.util.HashSet;
import java.util.stream.Stream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SignUpCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static UserService userService;
  private static Command command;

  @BeforeAll
  static void beforeAll() {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    userService = new UserService(mock(UserDAO.class));
    AirplaneService airplaneService = new AirplaneService(mock(AirplaneDAO.class));
    command = new SignUpCommand(userService, airplaneService);
  }

  @Test
  void executePasswordConfirmationFalseTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getParameter(FieldKey.PASSWORD)).thenReturn("password");
    when(request.getParameter(FieldKey.CONFIRM_PASSWORD)).thenReturn("password1");

    assertEquals(Constants.REDIRECT + Path.SIGN_UP_PAGE, command.execute(request));
  }

  @ParameterizedTest
  @MethodSource("invalidUser")
  void executeUserValidationFalseTest(String name, String surname, String login) {
    when(request.getSession()).thenReturn(session);
    when(request.getParameter(FieldKey.PASSWORD)).thenReturn("password");
    when(request.getParameter(FieldKey.CONFIRM_PASSWORD)).thenReturn("password");

    when(request.getParameter(FieldKey.FIRST_NAME)).thenReturn(name);
    when(request.getParameter(FieldKey.LAST_NAME)).thenReturn(surname);
    when(request.getParameter(FieldKey.LOGIN)).thenReturn(login);

    assertEquals(Constants.REDIRECT + Path.SIGN_UP_PAGE, command.execute(request));
  }

  @Test
  void executeUserAlreadyRegisteredTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getParameter(FieldKey.PASSWORD)).thenReturn("password");
    when(request.getParameter(FieldKey.CONFIRM_PASSWORD)).thenReturn("password");

    when(request.getParameter(FieldKey.FIRST_NAME)).thenReturn("Name");
    when(request.getParameter(FieldKey.LAST_NAME)).thenReturn("Surname");
    when(request.getParameter(FieldKey.LOGIN)).thenReturn("Login");

    when(userService.getUserByLogin(any(String.class))).thenReturn(new User());

    assertEquals(Constants.REDIRECT + Path.LOGIN_PAGE, command.execute(request));
  }
    @Test
    void executeTest() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(FieldKey.PASSWORD)).thenReturn("password");
        when(request.getParameter(FieldKey.CONFIRM_PASSWORD)).thenReturn("password");

        when(request.getParameter(FieldKey.FIRST_NAME)).thenReturn("Name");
        when(request.getParameter(FieldKey.LAST_NAME)).thenReturn("Surname");
        when(request.getParameter(FieldKey.LOGIN)).thenReturn("Login");

        when(userService.getUserByLogin(any(String.class))).thenReturn(null);
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getServletContext().getAttribute("loggedUsers")).thenReturn(new HashSet<String>());
//        when(airplaneService.getAllAirplanesForPage(Constants.PAGE_SIZE, 0)).thenReturn(List.of(new Airplane()));

        assertEquals(Constants.REDIRECT + Path.CLIENT_PAGE, command.execute(request));
    }

  static Stream<Arguments> invalidUser() {
    return Stream.of(
        Arguments.of("nam", "Surname", "Login123"),
        Arguments.of("Name", "1surnAme", "Login123"),
        Arguments.of("Name", "Surname", "Login-login"),
        Arguments.of("ame", "Su22name", "Login123"),
        Arguments.of("ame", "Surname", "l@g!n?"),
        Arguments.of("Name", "Surnam112e", "l@g!n?"),
        Arguments.of("me", "Surn_#ame", "l@g!n?"));
  }
}
