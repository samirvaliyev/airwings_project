package com.example.epamfinalproject.Controllers.Commands.Client;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Order;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Services.UserService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import com.example.epamfinalproject.Utility.SessionUtility;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A Command that collects data and creates an instance of an Order class and adds a record of it to the database
 */
public class CreateOrderCommand implements Command {
  private static final Logger log = LogManager.getLogger(CreateOrderCommand.class);
  private final OrderService orderService;
  private final UserService userService;
  private final AirplaneService airplaneService;

  public CreateOrderCommand(
      OrderService orderService, UserService userService, AirplaneService airplaneService) {
    this.orderService = orderService;
    this.userService = userService;
    this.airplaneService = airplaneService;
  }

  @Override
  public String execute(HttpServletRequest request){
    log.debug(Constants.COMMAND_STARTS);

    if(request.getSession().getAttribute("user") == null){
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ACCESS_DENIED);
      log.warn("Filter error! Accessed by an unauthenticated User");
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }
    User user = (User) request.getSession().getAttribute("user");

    Order order = shapeOrder(request);
    if(order == null){
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.AIRPLANE_INVALID);
      log.trace("Database has no Airplane with ID");
      log.debug(Constants.COMMAND_FINISHED);
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }

    List<Order> userOrders = orderService.getOrdersByUserID(user.getId());

    if (checkOrder(userOrders, order)) {
      log.debug("User already has the same order");
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ORDER_ALREADY_CREATED);
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }

    InputStream fileContent;
    long size;
    try {
      Part filePart = request.getPart("passport");
      fileContent = filePart.getInputStream();
      size = filePart.getSize();
    } catch (ServletException | IOException | NullPointerException e) {
      log.trace("Document data cannot be processed");
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }

    orderService.createOrder(order);
    userService.updateUserPassport(user.getId(), fileContent, size);

    userOrders = orderService.getOrdersByUserID(user.getId());

    SessionUtility.setOrders(request, userOrders);
    SessionUtility.updateUser(request,userService.getUserByID(user.getId()));
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.CATALOGUE_PAGE;
  }

  private Order shapeOrder(HttpServletRequest request) {
    Order order = new Order();
    order.setUser((User) request.getSession().getAttribute("user"));

    Airplane airplane =
        airplaneService.getAirplaneByID((Long.parseLong(request.getParameter(FieldKey.ENTITY_ID))));
    if(airplane == null){
      return null;
    }
    order.setAirplane(airplane);
    return order;
  }

  private boolean checkOrder(List<Order> orderList, Order order) {
    for (Order value : orderList) {
      if (value.equals(order)) {
        return true;
      }
    }
    return false;
  }
}
