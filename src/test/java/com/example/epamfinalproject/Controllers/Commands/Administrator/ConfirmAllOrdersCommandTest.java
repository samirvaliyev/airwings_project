package com.example.epamfinalproject.Controllers.Commands.Administrator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.OrderDAO;
import com.example.epamfinalproject.Entities.*;
import com.example.epamfinalproject.Entities.Enums.Status;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Utility.Constants;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConfirmAllOrdersCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;

  @BeforeAll
  static void beforeAll() {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    OrderDAO orderDAO = mock(OrderDAO.class);
    command = new ConfirmAllOrdersCommand(new OrderService(orderDAO));
  }

  @Test
  void confirmAllEmptyTest() {

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("orders")).thenReturn(Collections.emptyList());

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }

  @Test
  void confirmAllFilledTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("orders"))
        .thenReturn(List.of(new Order(2, new Airplane(), new User(), Status.PENDING)));

    assertEquals(Constants.REDIRECT + Path.CONFIRM_ORDERS, command.execute(request));
  }

  @Test
  void confirmAllNegativeTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("orders"))
        .thenReturn(List.of(new Order(-2, new Airplane(), new User(), Status.PENDING)));

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }
}
