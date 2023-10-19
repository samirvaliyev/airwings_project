package com.example.epamfinalproject.Services;

import com.example.epamfinalproject.Database.Interfaces.RouteDAO;
import com.example.epamfinalproject.Entities.Route;
import java.util.List;

public class RouteService {

  private final RouteDAO routeDAO;

  public RouteService(RouteDAO routeDAO) {
    this.routeDAO = routeDAO;
  }

  public void createRoute(Route route) {
    routeDAO.createRoute(route);
  }

  public void updateRouteByID(Route route, long id) {
    routeDAO.updateRouteByID(id, route);
  }

  public List<Route> getAllRoutes() {
    return routeDAO.getAllRoutes();
  }

  public Route getRouteByAllParameters(Route route) {
    return routeDAO.getRouteByAllParameters(route);
  }

  public Route getRouteByID(long id) {
    return routeDAO.getRouteByID(id);
  }
}
