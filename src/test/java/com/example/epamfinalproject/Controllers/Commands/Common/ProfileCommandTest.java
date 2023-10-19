package com.example.epamfinalproject.Controllers.Commands.Common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Utility.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProfileCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;

  @BeforeAll
  static void beforeAll() {
    session = mock(HttpSession.class);
    request = mock(HttpServletRequest.class);
    command = new ProfileCommand();
  }

  @Test
  void ClientProfileCommandTest() {
    User user = new User.UserBuilder().role(UserRole.CLIENT).build();
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(user);

    assertEquals(Constants.REDIRECT + Path.CLIENT_PAGE, command.execute(request));
  }

  @Test
  void AdminProfileCommandTest() {
    User user = new User.UserBuilder().role(UserRole.ADMINISTRATOR).build();
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(user);

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }

  @Test
  void NullProfileCommandTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(null);

    assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
  }
}
