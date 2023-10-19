package com.example.epamfinalproject.Controllers.Commands.Common;

import com.example.epamfinalproject.Controllers.Commands.Command;
import com.example.epamfinalproject.Controllers.Path;
import com.example.epamfinalproject.Database.Interfaces.*;
import com.example.epamfinalproject.Services.*;
import com.example.epamfinalproject.Utility.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogoutCommandTest {
    private static HttpServletRequest request;
    private static HttpSession session;
    private static Command command;

    @BeforeAll
    static void beforeAll() {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        command = new LogoutCommand();
    }
    @Test
    void executeSessionInvalidTest(){
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getServletContext().getAttribute("loggedUsers")).thenReturn(new HashSet<String>());

        assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));

        when(request.getSession()).thenReturn(null);
        assertEquals(Constants.REDIRECT + Path.MAIN_PAGE, command.execute(request));


    }
}