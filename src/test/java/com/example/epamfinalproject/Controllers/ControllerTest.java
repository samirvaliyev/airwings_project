package com.example.epamfinalproject.Controllers;

import static org.mockito.Mockito.*;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerTest {
  private static HttpServletRequest request;
  private static HttpServletResponse response;
  private static HttpSession session;
  private static RequestDispatcher dispatcher;
  private static Controller controller;

  @BeforeEach
  void beforeEach() {
    session = mock(HttpSession.class);
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    dispatcher = mock(RequestDispatcher.class);
    controller = new Controller();
  }

  @Test
  void doGetTest() throws IOException, javax.servlet.ServletException {

    when(request.getParameter("command")).thenReturn("testCommand");
    when(request.getRequestDispatcher(Path.MAIN_PAGE)).thenReturn(dispatcher);
    when(request.getSession()).thenReturn(session);

    controller.doGet(request, response);

    verify(dispatcher).forward(request, response);
  }

  @Test
  void doPostTest() throws IOException, ServletException {

    when(request.getParameter("command")).thenReturn("testCommand");
    when(request.getRequestDispatcher(Path.MAIN_PAGE)).thenReturn(dispatcher);
    when(request.getSession()).thenReturn(session);

    controller.doPost(request, response);
    verify(dispatcher).forward(request, response);
  }
}
