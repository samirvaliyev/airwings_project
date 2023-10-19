package com.example.epamfinalproject.Database.Shaper;

import com.example.epamfinalproject.Database.Implementations.StaffImplementation;
import com.example.epamfinalproject.Entities.Flight;
import com.example.epamfinalproject.Services.StaffService;
import com.example.epamfinalproject.Utility.FieldKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightShaper implements DataShaper<Flight> {
  StaffService staffService = new StaffService(new StaffImplementation());

  /**
   * @param resultSet result of SQL query execution
   * @return new instance of Flight class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public Flight shapeData(ResultSet resultSet) throws SQLException {
    Flight flight = new Flight();
    flight.setId(resultSet.getLong(FieldKey.ENTITY_ID));
    flight.setName(resultSet.getString(FieldKey.FLIGHT_NAME));
    flight.setStaff(staffService.getStaffByFlightID(flight.getId()));
    flight.setPassengerCapacity(resultSet.getInt(FieldKey.PASSENGER_CAPACITY));
    return flight;
  }

  /**
   * @param resultSet result of SQL query execution
   * @return list filled with new instances of Flight class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public List<Flight> shapeDataToList(ResultSet resultSet) throws SQLException {
    List<Flight> flightList = new ArrayList<>();
    while (resultSet.next()) {
      Flight flight = new Flight();
      flight.setId(resultSet.getLong(FieldKey.ENTITY_ID));
      flight.setName(resultSet.getString(FieldKey.FLIGHT_NAME));
      flight.setStaff(staffService.getStaffByFlightID(flight.getId()));
      flight.setPassengerCapacity(resultSet.getInt(FieldKey.PASSENGER_CAPACITY));
      flightList.add(flight);
    }
    return flightList;
  }
}
