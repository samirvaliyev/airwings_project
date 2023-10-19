package com.example.epamfinalproject.Controllers.Commands.Administrator;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Order;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.SessionUtility;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConfirmAllOrdersCommand implements Command {
  private static final Logger log = LogManager.getLogger(ConfirmAllOrdersCommand.class);

  private final OrderService orderService;

  public ConfirmAllOrdersCommand(OrderService orderService) {
    this.orderService = orderService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);
    List<Order> orders = (List<Order>) request.getSession().getAttribute("orders");

    if (orders.isEmpty()) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ORDER_CONFIRMATION_ERROR);
      log.trace("Not an Order has been ticked off");
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    for (Order order : orders) {
      if (order.getId() < 1) {
        request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ORDER_CONFIRMATION_ERROR);
        log.trace("Order ID is invalid");
        return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
      }
      orderService.confirmOrderByID(order.getId());
      log.debug("Record with id " + order.getId() + "was confirmed");
    }

    SessionUtility.setOrders(request, orderService.getAllUnconfirmedOrders());
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.CONFIRM_ORDERS;
  }
}
