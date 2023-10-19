package com.example.epamfinalproject.Controllers.Commands.Common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.*;
import com.example.epamfinalproject.Entities.*;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Services.*;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.Encryptor;
import com.example.epamfinalproject.Utility.FieldKey;
import java.util.*;
import java.util.stream.Stream;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LoginCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static UserService userService;
  private static Command command;

  @BeforeAll
  static void beforeAll() {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    userService = new UserService(mock(UserDAO.class));
    command =
        new LoginCommand(
            userService,
            new AirplaneService(mock(AirplaneDAO.class)),
            new OrderService(mock(OrderDAO.class)),
            new FlightService(mock(FlightDAO.class)),
            new RouteService(mock(RouteDAO.class)));
  }

  @Test
  void executeLoginInvalidTest() {
    when(request.getSession()).thenReturn(session);

    when(request.getParameter(FieldKey.LOGIN)).thenReturn(null);
    assertEquals(Constants.REDIRECT + Path.LOGIN_PAGE, command.execute(request));

    when(request.getParameter(FieldKey.LOGIN)).thenReturn("");
    assertEquals(Constants.REDIRECT + Path.LOGIN_PAGE, command.execute(request));
  }

  @Test
  void executeUserDataInvalidTest() {
    User user =
        new User.UserBuilder().login("Login").password(Encryptor.encrypt("password")).build();
    when(request.getSession()).thenReturn(session);
    when(request.getParameter(FieldKey.LOGIN)).thenReturn(user.getLogin());
    when(userService.getUserByLogin(any(String.class))).thenReturn(user);
    when(request.getParameter(FieldKey.PASSWORD)).thenReturn("password1");

    assertEquals(Constants.REDIRECT + Path.LOGIN_PAGE, command.execute(request));

    when(userService.getUserByLogin(any(String.class))).thenReturn(null);
    assertEquals(Constants.REDIRECT + Path.LOGIN_PAGE, command.execute(request));
  }

  @ParameterizedTest
  @MethodSource("validAdminData")
  void executeAdminDataValidTest(User user) {
    when(request.getSession()).thenReturn(session);
    when(request.getParameter(FieldKey.LOGIN)).thenReturn(user.getLogin());
    when(userService.getUserByLogin(any(String.class))).thenReturn(user);
    when(request.getParameter(FieldKey.PASSWORD)).thenReturn("password");
    when(request.getServletContext()).thenReturn(mock(ServletContext.class));
    when(request.getServletContext().getAttribute("loggedUsers")).thenReturn(new HashSet<String>());

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }

  @ParameterizedTest
  @MethodSource("validClientData")
  void executeClientDataValidTest(User user) {
    when(request.getSession()).thenReturn(session);
    when(request.getParameter(FieldKey.LOGIN)).thenReturn(user.getLogin());
    when(userService.getUserByLogin(any(String.class))).thenReturn(user);
    when(request.getParameter(FieldKey.PASSWORD)).thenReturn("password");
    when(request.getServletContext()).thenReturn(mock(ServletContext.class));
    when(request.getServletContext().getAttribute("loggedUsers")).thenReturn(new HashSet<String>());

    assertEquals(Constants.REDIRECT + Path.CLIENT_PAGE, command.execute(request));
  }

  static Stream<Arguments> validAdminData() {
    return Stream.of(
        Arguments.of(
            new User.UserBuilder()
                .login("Login")
                .password(Encryptor.encrypt("password"))
                .role(UserRole.ADMINISTRATOR)
                .build()),
        Arguments.of(
            new User.UserBuilder()
                .login("Login_Login")
                .password(Encryptor.encrypt("password"))
                .role(UserRole.ADMINISTRATOR)
                .build()));
  }

  static Stream<Arguments> validClientData() {
    return Stream.of(
        Arguments.of(
            new User.UserBuilder()
                .login("Login")
                .password(Encryptor.encrypt("password"))
                .role(UserRole.CLIENT)
                .build()),
        Arguments.of(
            new User.UserBuilder()
                .login("Login_Login")
                .password(Encryptor.encrypt("password"))
                .role(UserRole.CLIENT)
                .build()));
  }
}
