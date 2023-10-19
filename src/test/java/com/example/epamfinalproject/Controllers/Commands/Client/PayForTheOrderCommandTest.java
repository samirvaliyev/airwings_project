package com.example.epamfinalproject.Controllers.Commands.Client;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.OrderDAO;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Utility.Constants;
import com.example.epamfinalproject.Utility.FieldKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PayForTheOrderCommandTest {
    private static HttpServletRequest request;
    private static HttpSession session;
    private static Command command;
    private static OrderService orderService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        session = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
        user = new User.UserBuilder().id(1).role(UserRole.CLIENT).build();
        orderService = new OrderService(mock(OrderDAO.class));
        command = new PayForTheOrderCommand(orderService);
    }


    @Test
    void executeUserNullTest() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(null);
        assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
    }
    @Test
    void executeOrderNullTest() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn(null);

        assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
    }
    @Test
    void executeOrderNegativeTest() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn("-1");

        assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));
    }
    @Test
    void executeValidTest() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getParameter(FieldKey.ENTITY_ID)).thenReturn("1");
        when(orderService.getOrdersByUserID(any(Long.class))).thenReturn(new ArrayList<>());

        assertEquals(Constants.REDIRECT + Path.CLIENT_PAGE, command.execute(request));
    }

}