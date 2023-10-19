package com.example.epamfinalproject.Database.Implementations;

import com.example.epamfinalproject.Database.HikariConnectionPool;
import com.example.epamfinalproject.Database.Interfaces.RouteDAO;
import com.example.epamfinalproject.Database.Queries.RouteQueries;
import com.example.epamfinalproject.Database.Shaper.DataShaper;
import com.example.epamfinalproject.Database.Shaper.RouteShaper;
import com.example.epamfinalproject.Entities.Route;
import com.example.epamfinalproject.Utility.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class RouteImplementation implements RouteDAO {
  private static final Logger log = Logger.getLogger(RouteImplementation.class.getName());
  private PreparedStatement preparedStatement;
  DataShaper<Route> routeShaper = new RouteShaper();

  @Override
  public void createRoute(Route route) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(RouteQueries.CREATE_ROUTE_QUERY);
      preparedStatement.setString(1, route.getDeparture());
      preparedStatement.setString(2, route.getDestination());
      preparedStatement.setInt(3, route.getTransitTime());
      if (preparedStatement.executeUpdate() <= 0) {
        log.warn("Cannot create route.");
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
  public void updateRouteByID(long id, Route route) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      connection.setAutoCommit(false);
      preparedStatement = connection.prepareStatement(RouteQueries.UPDATE_ROUTE_QUERY);
      preparedStatement.setString(1, route.getDeparture());
      preparedStatement.setString(2, route.getDestination());
      preparedStatement.setInt(3, route.getTransitTime());
      preparedStatement.setLong(4, id);
      if (preparedStatement.executeUpdate() <= 0) {
        connection.rollback();
        log.warn("Cannot update route information.");
      }
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      log.warn(Constants.DATABASE_PROBLEM_WITH_CONNECTION + e);
      log.warn(e.toString());
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
  public Route getRouteByID(long id) {
    Route route = new Route();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(RouteQueries.GET_ROUTE_BY_ID_QUERY);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        route = routeShaper.shapeData(resultSet);
      }
    } catch (SQLException e) {
      log.warn(Constants.DATABASE_PROBLEM_WITH_CONNECTION + e);
      log.warn(e.toString());
    } finally {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          log.warn(Constants.DATABASE_ERROR_CLOSING_CONNECTION);
        }
      }
    }
    return route;
  }

  @Override
  public List<Route> getAllRoutes() {
    List<Route> routeList = new ArrayList<>();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(RouteQueries.GET_ALL_ROUTES_QUERY);
      ResultSet resultSet = preparedStatement.executeQuery();
      routeList = routeShaper.shapeDataToList(resultSet);
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
    return routeList;
  }

  @Override
  public Route getRouteByAllParameters(Route route) {
    Route routeRecord = new Route();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement =
          connection.prepareStatement(RouteQueries.GET_ROUTE_BY_ALL_PARAMETERS_QUERY);
      preparedStatement.setString(1, route.getDeparture());
      preparedStatement.setString(2, route.getDestination());
      preparedStatement.setInt(3, route.getTransitTime());
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        routeRecord = routeShaper.shapeData(resultSet);
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
    return routeRecord;
  }
}
