package com.example.epamfinalproject.Database.Implementations;

import com.example.epamfinalproject.Database.HikariConnectionPool;
import com.example.epamfinalproject.Database.Interfaces.AirplaneDAO;
import com.example.epamfinalproject.Database.Queries.AirplaneQueries;
import com.example.epamfinalproject.Database.Queries.RouteQueries;
import com.example.epamfinalproject.Database.Queries.FlightQueries;
import com.example.epamfinalproject.Database.Shaper.AirplaneShaper;
import com.example.epamfinalproject.Database.Shaper.DataShaper;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Services.StaffService;
import com.example.epamfinalproject.Utility.Constants;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class AirplaneImplementation implements AirplaneDAO {
  private static final Logger log = Logger.getLogger(AirplaneImplementation.class.getName());

  PreparedStatement preparedStatement;
  DataShaper<Airplane> airplaneShaper = new AirplaneShaper(new StaffService(new StaffImplementation()));

  @Override
  public void createAirplane(Airplane airplane) {
    long flightID = 0;
    long routeID = 0;
    try (Connection connection = HikariConnectionPool.getConnection()) {
      connection.setAutoCommit(false);
      preparedStatement = connection.prepareStatement(FlightQueries.REGISTER_FLIGHT_QUERY_RETURNING_ID);
      preparedStatement.setString(1, airplane.getFlight().getName());
      preparedStatement.setInt(2, airplane.getFlight().getPassengerCapacity());
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) flightID = resultSet.getLong(1);

      preparedStatement = connection.prepareStatement(RouteQueries.CREATE_ROUTE_QUERY_RETURNING_ID);
      preparedStatement.setString(1, airplane.getRoute().getDeparture());
      preparedStatement.setString(2, airplane.getRoute().getDestination());
      preparedStatement.setInt(3, airplane.getRoute().getTransitTime());
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) routeID = resultSet.getLong(1);

      preparedStatement = connection.prepareStatement(AirplaneQueries.CREATE_AIRPLANE_QUERY);
      preparedStatement.setLong(1, flightID);
      preparedStatement.setLong(2, routeID);
      preparedStatement.setString(3, airplane.getName());
      preparedStatement.setInt(4, airplane.getPrice());
      preparedStatement.setDate(5, Date.valueOf(airplane.getStartOfTheAirplane()));
      preparedStatement.setDate(6, Date.valueOf(airplane.getEndOfTheAirplane()));
      if (preparedStatement.executeUpdate() <= 0) {
        log.warn("Cannot add airplane information.");
        connection.rollback();
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

  @Override
  public void updateAirplaneByID(Airplane airplane, long id) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      connection.setAutoCommit(false);
      preparedStatement = connection.prepareStatement(AirplaneQueries.UPDATE_AIRPLANE_BY_ID_QUERY);
      preparedStatement.setLong(1, airplane.getFlight().getId());
      preparedStatement.setLong(2, airplane.getRoute().getId());
      preparedStatement.setString(3, airplane.getName());
      preparedStatement.setInt(4, airplane.getPrice());
      preparedStatement.setDate(5, java.sql.Date.valueOf(airplane.getStartOfTheAirplane()));
      preparedStatement.setDate(6, java.sql.Date.valueOf(airplane.getEndOfTheAirplane()));
      preparedStatement.setLong(7, id);
      if (preparedStatement.executeUpdate() <= 0) {
        connection.rollback();
        log.warn(Constants.DATABASE_ERROR_CLOSING_CONNECTION);
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

  @Override
  public void deleteAirplaneByID(long id) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      connection.setAutoCommit(false);
      preparedStatement = connection.prepareStatement(AirplaneQueries.DELETE_AIRPLANE_BY_ID_QUERY);
      preparedStatement.setLong(1, id);
      if (preparedStatement.executeUpdate() <= 0) {
        connection.rollback();
        log.warn(Constants.DATABASE_ERROR_CLOSING_CONNECTION);
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

  @Override
  public void confirmAirplaneByID(long id) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      connection.setAutoCommit(false);
      preparedStatement = connection.prepareStatement(AirplaneQueries.CONFIRM_AIRPLANE_BY_ID_QUERY);
      preparedStatement.setLong(1, id);
      if (preparedStatement.executeUpdate() <= 0) {
        connection.rollback();
        log.warn(Constants.DATABASE_ERROR_CLOSING_CONNECTION);
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

  @Override
  public int getNumberOfActualAirplanes(String query) {
    int rowsCount = 0;
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        rowsCount = resultSet.getInt(1);
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
    return rowsCount;
  }

  @Override
  public Airplane getAirplaneByID(long id) {
    Airplane airplane = null;
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(AirplaneQueries.GET_AIRPLANE_BY_ID);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        airplane = airplaneShaper.shapeData(resultSet);
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
    return airplane;
  }

  @Override
  public List<Airplane> getAllAirplanesForPage(String query) {
    List<Airplane> airplaneList = new ArrayList<>();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet != null) {
        airplaneList = airplaneShaper.shapeDataToList(resultSet);
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
    return airplaneList;
  }

}
