package com.example.epamfinalproject.Controllers.Commands.Common;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.MessageKeys;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import com.example.epamfinalproject.Utility.SessionUtility;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * A command that makes a query to the database and passes complete information about the airplane to the session.
 * If the registered user is Administrator, then redirect to the editing page. Redirects the Client to the order page
 */
public class DisplayFormWithAirplaneInfoCommand implements Command {
    private static final Logger log = LogManager.getLogger(DisplayFormWithAirplaneInfoCommand.class);

    private final AirplaneService airplaneService;
    private final OrderService orderService;
    public DisplayFormWithAirplaneInfoCommand(AirplaneService airplaneService, OrderService orderService) {
        this.airplaneService = airplaneService;
        this.orderService = orderService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        log.debug(Constants.COMMAND_STARTS);

        if(request.getSession().getAttribute("user")  == null){
            request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.ACCESS_DENIED);
            log.warn("Filter error! Accessed by an unauthenticated User");
            log.debug(Constants.COMMAND_FINISHED);
            return Constants.REDIRECT + Path.MAIN_PAGE;
        }
        User user = (User) request.getSession().getAttribute("user");

        Airplane airplane =
                airplaneService.getAirplaneByID(Long.parseLong(request.getParameter(FieldKey.ENTITY_ID)));
        if(airplane == null){
            request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.AIRPLANE_INVALID);
            log.trace("Database has no Airplane with ID");
            log.debug(Constants.COMMAND_FINISHED);
            return Constants.REDIRECT + Path.MAIN_PAGE;
        }
        if(user.getRole().equals(UserRole.CLIENT)) {
            int freeSeats =
                    airplane.getFlight().getPassengerCapacity()
                            - orderService.getBookedSeatsByAirplaneID(airplane.getId());
            if (freeSeats == 0) {
                request.getSession().setAttribute(Constants.MESSAGE, MessageKeys.FLIGHT_INACCESSIBLE);
                log.trace("Airplane has no free seats");
                log.debug(Constants.COMMAND_FINISHED);
                return Constants.REDIRECT + Path.CATALOGUE_PAGE;
            }
            SessionUtility.setAirplaneParamsForClient(request, airplane,freeSeats);
            log.debug(Constants.COMMAND_FINISHED);
            return Constants.REDIRECT + Path.ORDER_PAGE;
        }else {
            SessionUtility.setAirplaneParamsForAdmin(request, airplane);
        }
        log.debug(Constants.COMMAND_FINISHED);

        return Constants.REDIRECT + Path.EDIT_AIRPLANE_PAGE;
    }
}
