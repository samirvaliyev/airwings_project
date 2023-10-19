package com.example.epamfinalproject.Controllers.Commands.Common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Utility.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ChangeLocaleCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;

  @BeforeAll
  static void beforeAll() {
    session = mock(HttpSession.class);
    request = mock(HttpServletRequest.class);
    command = new ChangeLocaleCommand();
  }

  @ParameterizedTest
  @ValueSource(strings = {"en", "ua", "ur"})
  void changeLocaleTest(String value) {
    when(request.getSession()).thenReturn(session);
    when(request.getParameter("page-path")).thenReturn("/test.jsp");
    when(request.getParameter("locale")).thenReturn(value);
    assertEquals(Constants.REDIRECT + "/test.jsp", command.execute(request));
  }
}
