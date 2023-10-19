package com.example.epamfinalproject.Controllers.Commands.Administrator;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import com.example.epamfinalproject.Utility.SessionUtility;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Administrator order confirmation command
 */
public class ConfirmOrderCommand implements Command {
  private static final Logger log = LogManager.getLogger(ConfirmOrderCommand.class);

  private final OrderService orderService;

  public ConfirmOrderCommand(OrderService orderService) {
    this.orderService = orderService;
  }

  @Override
  public String execute(HttpServletRequest request) {
    log.debug(Constants.COMMAND_STARTS);
    List<String> params = List.of(request.getParameterValues(FieldKey.ENTITY_ID));

    if (params.isEmpty()) {
      request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ORDER_CONFIRMATION_ERROR);
      log.trace("Not an Order has been ticked off");
      return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
    }
    for (String param : params) {
      if(Long.parseLong(param) < 1){
        request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ORDER_CONFIRMATION_ERROR);
        log.trace("Order ID is invalid");
        return Constants.REDIRECT + Path.ADMINISTRATOR_PAGE;
      }
      orderService.confirmOrderByID(Long.parseLong(param));
      log.debug("Record with id " + Integer.parseInt(param) + "was confirmed");
    }

    SessionUtility.setOrders(request, orderService.getAllUnconfirmedOrders());
    log.debug(Constants.COMMAND_FINISHED);
    return Constants.REDIRECT + Path.CONFIRM_ORDERS;
  }
}
