package com.example.epamfinalproject.Controllers.Commands.Client;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import com.example.epamfinalproject.Utility.SessionUtility;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PayForTheOrderCommand implements Command {
  private static final Logger log = LogManager.getLogger(PayForTheOrderCommand.class);
  private final OrderService orderService;

  public PayForTheOrderCommand(OrderService orderService) {
    this.orderService = orderService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);
    if (request.getSession().getAttribute("user") == null) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ACCESS_DENIED);
      log.warn("Filter error! Accessed by an unauthenticated User");
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }
    User user = (User) request.getSession().getAttribute("user");

    String param = request.getParameter(FieldKey.ENTITY_ID);

    if (param == null) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ORDER_CONFIRMATION_ERROR);
      log.trace("Not an Order has been ticked off");
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }
    if (Long.parseLong(param) < 1) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ORDER_CONFIRMATION_ERROR);
      log.trace("Order ID is invalid");
      return Constants.REDIRECT + Path.MAIN_PAGE;
    }
    orderService.payForTheOrderByID(Long.parseLong(param));
    log.debug("Record with id " + Integer.parseInt(param) + "was paid");

    SessionUtility.setOrders(request, orderService.getOrdersByUserID(user.getId()));
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.CLIENT_PAGE;
  }
}
