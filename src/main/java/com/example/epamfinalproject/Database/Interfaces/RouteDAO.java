package com.example.epamfinalproject.Database.Interfaces;

import com.example.epamfinalproject.Entities.Route;
import java.util.List;

public interface RouteDAO {
  void createRoute(Route route);

  void updateRouteByID(long id, Route route);

  Route getRouteByID(long id);

  List<Route> getAllRoutes();

  Route getRouteByAllParameters(Route route);
}
