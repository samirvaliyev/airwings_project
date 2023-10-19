package com.example.epamfinalproject.Database.Implementations;

import com.example.epamfinalproject.Database.HikariConnectionPool;
import com.example.epamfinalproject.Database.Interfaces.FlightDAO;
import com.example.epamfinalproject.Database.Queries.FlightQueries;
import com.example.epamfinalproject.Database.Shaper.DataShaper;
import com.example.epamfinalproject.Database.Shaper.FlightShaper;
import com.example.epamfinalproject.Entities.Flight;
import com.example.epamfinalproject.Utility.Constants;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class FlightImplementation implements FlightDAO {
  private static final Logger log = Logger.getLogger(FlightImplementation.class.getName());
  private PreparedStatement preparedStatement;
  DataShaper<Flight> flightShaper = new FlightShaper();

  @Override
  public void registerFlight(Flight flight) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(FlightQueries.REGISTER_FLIGHT_QUERY);
      preparedStatement.setString(1, flight.getName());
      preparedStatement.setInt(2, flight.getPassengerCapacity());
      if (preparedStatement.executeUpdate() <= 0) {
        log.warn("Cannot register flight.");
      }
    } catch (SQLException e) {
      log.warn(Constants.DATABASE_PROBLEM_WITH_CONNECTION + e);
    } finally {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          log.warn(Constants.DATABASE_ERROR_CLOSING_CONNECTION);
        }
      }
    }
  }

  @Override
  public Flight getFlightByName(String name) {
    Flight flight = new Flight();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(FlightQueries.GET_FLIGHT_BY_NAME_QUERY);
      preparedStatement.setString(1, name);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        flight = flightShaper.shapeData(resultSet);
      }
    } catch (SQLException e) {
      log.warn(Constants.DATABASE_PROBLEM_WITH_CONNECTION + e);
    } finally {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          log.warn(Constants.DATABASE_ERROR_CLOSING_CONNECTION);
        }
      }
    }
    return flight;
  }

  @Override
  public List<Flight> getAllFlights() {
    List<Flight> flightList = new ArrayList<>();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(FlightQueries.GET_ALL_FLIGHTS_QUERY);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet != null) {
        flightList = flightShaper.shapeDataToList(resultSet);
      }
    } catch (SQLException e) {
      log.warn(Constants.DATABASE_PROBLEM_WITH_CONNECTION + e);
    } finally {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          log.warn(Constants.DATABASE_ERROR_CLOSING_CONNECTION);
        }
      }
    }
    return flightList;
  }

  @Override
  public void updateFlightByID(Flight flight, long id) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      connection.setAutoCommit(false);
      preparedStatement = connection.prepareStatement(FlightQueries.UPDATE_FLIGHT_BY_ID_QUERY);
      preparedStatement.setString(1, flight.getName());
      preparedStatement.setInt(2, flight.getPassengerCapacity());
      preparedStatement.setLong(3, id);
      if (preparedStatement.executeUpdate() <= 0) {
        connection.rollback();
        log.warn("Cannot update flight.");
      }
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      log.warn(Constants.DATABASE_PROBLEM_WITH_CONNECTION + e);
    } finally {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          log.warn(Constants.DATABASE_ERROR_CLOSING_CONNECTION);
        }
      }
    }
  }
}
