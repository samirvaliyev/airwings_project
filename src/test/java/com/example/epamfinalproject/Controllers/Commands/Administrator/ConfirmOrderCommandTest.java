package com.example.epamfinalproject.Controllers.Commands.Administrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.OrderDAO;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConfirmOrderCommandTest {
  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;

  @BeforeAll
  static void beforeAll() {
    request = mock(HttpServletRequest.class);
    session = mock(HttpSession.class);
    OrderDAO orderDAO = mock(OrderDAO.class);
    command = new ConfirmOrderCommand(new OrderService(orderDAO));
  }

  @Test
  void confirmOrderEmptyTest() {

    when(request.getSession()).thenReturn(session);
    when(request.getParameterValues(FieldKey.ENTITY_ID)).thenReturn(new String[] {});

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }

  @Test
  void confirmOrderFilledTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getParameterValues(FieldKey.ENTITY_ID))
        .thenReturn(new String[] {"2", "3", "5", "7"});

    assertEquals(Constants.REDIRECT + Path.CONFIRM_ORDERS, command.execute(request));
  }

  @Test
  void confirmNegativeOrderTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getParameterValues(FieldKey.ENTITY_ID))
        .thenReturn(new String[] {"2", "3", "-5", "7"});

    assertEquals(Constants.REDIRECT + Path.ADMINISTRATOR_PAGE, command.execute(request));
  }
}
