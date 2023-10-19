package com.example.epamfinalproject.Database.Shaper;

import com.example.epamfinalproject.Entities.Route;
import com.example.epamfinalproject.Utility.FieldKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteShaper implements DataShaper<Route> {
  /**
   * @param resultSet result of SQL query execution
   * @return new instance of Route class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public Route shapeData(ResultSet resultSet) throws SQLException {
    Route route = new Route();
    route.setId(resultSet.getInt(FieldKey.ENTITY_ID));
    route.setDeparture(resultSet.getString(FieldKey.DEPARTURE));
    route.setDestination(resultSet.getString(FieldKey.DESTINATION));
    route.setTransitTime(resultSet.getInt(FieldKey.TRANSIT_TIME));
    return route;
  }

  /**
   * @param resultSet result of SQL query execution
   * @return list filled with new instances of Route class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public List<Route> shapeDataToList(ResultSet resultSet) throws SQLException {
    List<Route> routeList = new ArrayList<>();
    while (resultSet.next()) {
      Route route = new Route();
      route.setId(resultSet.getInt(FieldKey.ENTITY_ID));
      route.setDeparture(resultSet.getString(FieldKey.DEPARTURE));
      route.setDestination(resultSet.getString(FieldKey.DESTINATION));
      route.setTransitTime(resultSet.getInt(FieldKey.TRANSIT_TIME));
      routeList.add(route);
    }
    return routeList;
  }
}
