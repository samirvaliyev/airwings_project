package com.example.epamfinalproject.Controllers.Commands.Common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.*;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.Flight;
import com.example.epamfinalproject.Entities.Route;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.*;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DisplayFormWithAirplaneInfoCommandTest {

  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;
  private static Airplane airplane;
  private static AirplaneService airplaneService;
  private static OrderService orderService;
  private static User admin;
  private static User client;

  @BeforeAll
  static void beforeAll() {
    session = mock(HttpSession.class);
    request = mock(HttpServletRequest.class);
    admin = new User.UserBuilder().role(UserRole.ADMINISTRATOR).build();
    client = new User.UserBuilder().role(UserRole.CLIENT).build();
    airplaneService = new AirplaneService(mock(AirplaneDAO.class));
    orderService = new OrderService(mock(OrderDAO.class));
    airplane =
        new Airplane(
            new Flight(1, "Name", 1),
                new Route(1, "Value", "Value", 4),
                "Name", 0, null, null);
    command = new DisplayFormWithAirplaneInfoCommand(airplaneService, orderService);
  }

  @Test
  void DisplayFormAirplaneNullTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(client);
    when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn("1");
    when(airplaneService.getAirplaneByID(any(Long.class))).thenReturn(null);
    assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
  }

  @Test
  void DisplayFormNotNullClientTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(client);
    when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn("1");
    when(airplaneService.getAirplaneByID(any(Long.class))).thenReturn(airplane);
    when(orderService.getBookedSeatsByAirplaneID(airplane.getId())).thenReturn(0);
    assertEquals(Constants.REDIRECT + Path.ORDER_PAGE, command.execute(request));
  }

  @Test
  void DisplayFormNotNullAdminTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(admin);
    when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn("1");
    when(airplaneService.getAirplaneByID(any(Long.class))).thenReturn(new Airplane());
    assertEquals(Constants.REDIRECT + Path.EDIT_AIRPLANE_PAGE, command.execute(request));
  }

  @Test
  void DisplayFormEmptyUserTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(null);

    assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
  }

  @Test
  void DisplayFormNotFreeSeatsTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn("1");
    when(request.getSession().getAttribute("user")).thenReturn(client);
    when(airplaneService.getAirplaneByID(any(Long.class))).thenReturn(airplane);
    when(orderService.getBookedSeatsByAirplaneID(airplane.getId())).thenReturn(1);

    assertEquals(Constants.REDIRECT + Path.CATALOGUE_PAGE, command.execute(request));
  }
}
