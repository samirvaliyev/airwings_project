package com.example.epamfinalproject.Database.Shaper;

import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Flight;
import com.example.epamfinalproject.Entities.Route;
import com.example.epamfinalproject.Services.StaffService;
import com.example.epamfinalproject.Utility.FieldKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AirplaneShaper implements DataShaper<Airplane> {

  private final StaffService staffService;

  public AirplaneShaper(StaffService staffService) {
    this.staffService = staffService;
  }

  /**
   * @param resultSet result of SQL query execution
   * @return new instance of Airplane class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public Airplane shapeData(ResultSet resultSet) throws SQLException {
    Airplane airplane = new Airplane();
    Flight flight;
    Route route;
    airplane.setId(resultSet.getLong(FieldKey.ENTITY_ID));
    airplane.setName(resultSet.getString(FieldKey.AIRPLANE_NAME));
    airplane.setPrice(resultSet.getInt(FieldKey.AIRPLANE_PRICE));
    airplane.setDeleted(resultSet.getString(FieldKey.AIRPLANE_DELETED).equals("t"));
    airplane.setConfirmed(resultSet.getString(FieldKey.AIRPLANE_CONFIRMED).equals("t"));
    airplane.setStartOfTheAirplane(LocalDate.parse(resultSet.getString(FieldKey.AIRPLANE_LEAVING)));
    airplane.setEndOfTheAirplane((LocalDate.parse(resultSet.getString(FieldKey.AIRPLANE_ARRIVING))));

    route = routeShaper(resultSet);
    flight = flightShaper(resultSet);

    flight.setStaff(staffService.getStaffByFlightID(resultSet.getLong(FieldKey.AIRPLANE_FLIGHT_ID)));

    airplane.setFlight(flight);
    airplane.setRoute(route);

    return airplane;
  }

  /**
   * @param resultSet result of SQL query execution
   * @return list filled with new instances of Airplane class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public List<Airplane> shapeDataToList(ResultSet resultSet) throws SQLException {
    List<Airplane> airplaneList = new ArrayList<>();
    Flight flight;
    Route route;
    while (resultSet.next()) {
      Airplane airplane = new Airplane();
      airplane.setId(resultSet.getLong(FieldKey.ENTITY_ID));
      airplane.setName(resultSet.getString(FieldKey.AIRPLANE_NAME));
      airplane.setPrice(resultSet.getInt(FieldKey.AIRPLANE_PRICE));
      airplane.setDeleted(resultSet.getString(FieldKey.AIRPLANE_DELETED).equals("t"));
      airplane.setConfirmed(Boolean.parseBoolean(resultSet.getString(FieldKey.AIRPLANE_CONFIRMED)));
      airplane.setStartOfTheAirplane(LocalDate.parse(resultSet.getString(FieldKey.AIRPLANE_LEAVING)));
      airplane.setEndOfTheAirplane((LocalDate.parse(resultSet.getString(FieldKey.AIRPLANE_ARRIVING))));

      route = routeShaper(resultSet);
      flight = flightShaper(resultSet);

      flight.setStaff(staffService.getStaffByFlightID(resultSet.getLong(FieldKey.AIRPLANE_FLIGHT_ID)));

      airplane.setFlight(flight);
      airplane.setRoute(route);
      airplaneList.add(airplane);
    }
    return airplaneList;
  }

  /**
   * Helper function for forming an instance of the Flight class.
   *
   * @param resultSet result of SQL query execution
   * @return new instance of Flight class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  private Flight flightShaper(ResultSet resultSet) throws SQLException {
    Flight flight = new Flight();
    flight.setId(resultSet.getLong(FieldKey.AIRPLANE_FLIGHT_ID));
    flight.setName(resultSet.getString(FieldKey.FLIGHT_NAME));
    flight.setPassengerCapacity(resultSet.getInt(FieldKey.PASSENGER_CAPACITY));
    return flight;
  }

  /**
   * Helper function for forming an instance of the Route class.
   *
   * @param resultSet result of SQL query execution
   * @return new instance of Route class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  private Route routeShaper(ResultSet resultSet) throws SQLException {
    Route route = new Route();
    route.setId(resultSet.getInt(FieldKey.AIRPLANE_ROUTE_ID));
    route.setDeparture(resultSet.getString(FieldKey.DEPARTURE));
    route.setDestination(resultSet.getString(FieldKey.DESTINATION));
    route.setTransitTime(resultSet.getInt(FieldKey.TRANSIT_TIME));
    return route;
  }
}
