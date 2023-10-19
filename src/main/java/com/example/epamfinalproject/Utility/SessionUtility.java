package com.example.epamfinalproject.Utility;

import com.example.epamfinalproject.Entities.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Util class to set session parameters after each command Also updates loggedUsers parameter if
 * login/logout command executed
 */
public class SessionUtility {
  private static final String LOGIN = "login";
  private static final String AIRPLANES = "airplanes";
  private static final String ORDERS = "orders";

  private SessionUtility() {}

  /**
   * Sets Session parameters for ADMINISTRATOR user
   *
   * @param user administrator info
   * @param users list of all users in system
   * @param orders all unconfirmed orders
   */
  public static void setParamsForAdmin(
      HttpServletRequest request,
      User user,
      List<User> users,
      List<Order> orders) {
    HttpSession session = request.getSession();
    ServletContext context = request.getServletContext();
    HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
    session.setAttribute("role", user.getRole().toString());
    session.setAttribute("user", user);
    loggedUsers.add(user.getLogin());
    session.setAttribute(LOGIN, user.getLogin());
    session.setAttribute("users", users);
    session.setAttribute(ORDERS, orders);
  }

  public static void updateUser(HttpServletRequest request, User user) {
    HttpSession session = request.getSession();
    session.setAttribute("role", user.getRole().toString());
    session.setAttribute("user", user);
    session.setAttribute(LOGIN, user.getLogin());
    if (user.getPassport() != null) {
      request
          .getSession()
          .setAttribute(
              FieldKey.PASSPORT,
              "data:image/png;base64,"
                  + new String(
                      Base64.getEncoder().encode(user.getPassport()), StandardCharsets.UTF_8));
    }
  }

  /**
   * Sets Session parameters for CLIENT user
   *
   * @param user client info
   * @param airplanes all actual airplanes
   * @param orders all User's orders
   */
  public static void setParamsForClient(
          HttpServletRequest request, User user, List<Airplane> airplanes, List<Order> orders) {
    HttpSession session = request.getSession();
    ServletContext context = request.getServletContext();
    HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
    loggedUsers.add(user.getLogin());
    session.setAttribute("role", user.getRole().toString());
    session.setAttribute("user", user);
    session.setAttribute(LOGIN, user.getLogin());
    session.setAttribute(AIRPLANES, airplanes);
    session.setAttribute(ORDERS, orders);
    if (user.getPassport() != null) {
      request
          .getSession()
          .setAttribute(
              FieldKey.PASSPORT,
              "data:image/png;base64,"
                  + new String(
                      Base64.getEncoder().encode(user.getPassport()), StandardCharsets.UTF_8));
    }
  }

  /**
   * Sets Session parameter after creating new Airplane
   *
   * @param airplanes all actual airplanes
   */
  public static void setAirplanesParams(HttpServletRequest request, List<Airplane> airplanes) {
    HttpSession session = request.getSession();
    session.setAttribute(AIRPLANES, airplanes);
  }

  /**
   * Sets Session parameters after creating new Flight
   *
   * @param airplane The Airplane selected by the administrator for editing
   */
  public static void setAirplaneParamsForAdmin(HttpServletRequest request, Airplane airplane) {
    HttpSession session = request.getSession();
    session.setAttribute("airplane", airplane);
  }

  /** Set information about the Airplane that the user has chosen */
  public static void setAirplaneParamsForClient(
          HttpServletRequest request, Airplane airplane, int freeSeats) {
    HttpSession session = request.getSession();
    session.setAttribute("airplane", airplane);
    session.setAttribute("freeSeats", freeSeats);
  }

  /**
   * Sets ORDER with it status after CLIENT created one or after ADMINISTRATOR confirm one
   *
   * @param orders list of orders
   */
  public static void setOrders(HttpServletRequest request, List<Order> orders) {
    HttpSession session = request.getSession();
    session.setAttribute(ORDERS, orders);
  }
}
