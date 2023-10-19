package com.example.epamfinalproject.Controllers.Commands.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.*;
import com.example.epamfinalproject.Entities.*;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.Order;
import com.example.epamfinalproject.Services.*;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CreateOrderCommandTest {

  private static HttpServletRequest request;
  private static HttpSession session;
  private static Command command;
  private static Airplane airplane;
  private static AirplaneService airplaneService;
  private static OrderService orderService;
  private static UserService userService;
  private static User user;

  @BeforeAll
  static void beforeAll() {
    session = mock(HttpSession.class);
    request = mock(HttpServletRequest.class);
    user = new User.UserBuilder().id(1).role(UserRole.CLIENT).build();
    airplaneService = new AirplaneService(mock(AirplaneDAO.class));
    orderService = new OrderService(mock(OrderDAO.class));
    userService = new UserService(mock(UserDAO.class));
    airplane =
        new Airplane(
            2,
            "Name",
            new Flight(2, "Name", 1),
            new Route(2, "Value", "Value", 4),
            0,
            false,
            false,
            null,
            null);
    command = new CreateOrderCommand(orderService, userService, airplaneService);
  }

  @Test
  void executeUserNullTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(null);
    assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
  }

  @Test
  void executeOrderInvalidTest() {
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(user);
    when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn(String.valueOf(user.getId()));
    when(airplaneService.getAirplaneByID(any(Long.class))).thenReturn(null);

    assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
  }

  @Test
  void executeCheckOrderInvalidTest() {
    Order[] orders = {new Order(new Airplane(new Flight(), new Route(), "name", 0, null, null), user)};
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(user);
    when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn(String.valueOf(user.getId()));
    when(airplaneService.getAirplaneByID(any(Long.class))).thenReturn(new Airplane());
    when(orderService.getOrdersByUserID(any(Long.class))).thenReturn(List.of(orders));

    assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
  }

  @Test
  void executePassportInvalidTest() throws ServletException, IOException {
    Order[] orders = {new Order(airplane, user)};
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(user);
    when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn(String.valueOf(user.getId()));
    when(airplaneService.getAirplaneByID(any(Long.class))).thenReturn(new Airplane());
    when(orderService.getOrdersByUserID(any(Long.class))).thenReturn(List.of(orders));

    when(request.getPart("passport")).thenReturn(null);

    assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
  }

  @Test
  void executeValidTest() throws ServletException, IOException {
    Order[] orders = {new Order(airplane, user)};
    when(request.getSession()).thenReturn(session);
    when(request.getSession().getAttribute("user")).thenReturn(user);
    when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn(String.valueOf(user.getId()));
    when(airplaneService.getAirplaneByID(any(Long.class))).thenReturn(new Airplane());
    when(orderService.getOrdersByUserID(any(Long.class))).thenReturn(List.of(orders));

    when(request.getPart("passport"))
        .thenReturn(
            new Part() {
              @Override
              public InputStream getInputStream() throws IOException {
                return null;
              }

              @Override
              public String getContentType() {
                return null;
              }

              @Override
              public String getName() {
                return null;
              }

              @Override
              public String getSubmittedFileName() {
                return null;
              }

              @Override
              public long getSize() {
                return 0;
              }

              @Override
              public void write(String s) throws IOException {}

              @Override
              public void delete() throws IOException {}

              @Override
              public String getHeader(String s) {
                return null;
              }

              @Override
              public Collection<String> getHeaders(String s) {
                return null;
              }

              @Override
              public Collection<String> getHeaderNames() {
                return null;
              }
            });
    when(userService.getUserByID(any(Long.class)))
        .thenReturn(
            new User.UserBuilder()
                .login("Login")
                .role(UserRole.CLIENT)
                .passport(new byte[] {})
                .build());

    assertEquals(Constants.REDIRECT + Path.CATALOGUE_PAGE, command.execute(request));
  }
}
