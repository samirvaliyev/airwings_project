package com.example.epamfinalproject.Controllers.Commands.Administrator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.AirplaneDAO;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DeleteAirplaneCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;

  @BeforeAll
  static void beforeAll() {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    AirplaneDAO airplaneDAO = mock(AirplaneDAO.class);
    command = new DeleteAirplaneCommand(new AirplaneService(airplaneDAO));
  }

  @Test
  void deleteAirplaneEmptyTest() {
    String[] values = {};
    when(request.getSession()).thenReturn(session);
    when(request.getParameterValues(FieldKey.ENTITY_ID)).thenReturn(values);

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }

  @Test
  void deleteAirplaneFilledTest() {
    String[] values = {"2", "3", "5", "7"};
    when(request.getSession()).thenReturn(session);
    when(request.getParameterValues(FieldKey.ENTITY_ID)).thenReturn(values);

    assertEquals(Constants.REDIRECT + Path.DELETE_AIRPLANE_PAGE, command.execute(request));
  }

  @Test
  void deleteAirplaneInvalidTest() {
    String[] values = {"-2", "-3", "-5", "-7"};
    when(request.getSession()).thenReturn(session);
    when(request.getParameterValues(FieldKey.ENTITY_ID)).thenReturn(values);

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }
}
