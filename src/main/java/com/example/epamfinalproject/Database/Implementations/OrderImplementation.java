package com.example.epamfinalproject.Database.Implementations;

import com.example.epamfinalproject.Database.HikariConnectionPool;
import com.example.epamfinalproject.Database.Interfaces.OrderDAO;
import com.example.epamfinalproject.Database.Queries.OrderQueries;
import com.example.epamfinalproject.Database.Shaper.DataShaper;
import com.example.epamfinalproject.Database.Shaper.OrderShaper;
import com.example.epamfinalproject.Entities.Order;
import com.example.epamfinalproject.Utility.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class OrderImplementation implements OrderDAO {
  private static final Logger log = Logger.getLogger(OrderImplementation.class.getName());
  PreparedStatement preparedStatement;
  DataShaper<Order> orderShaper = new OrderShaper();

  @Override
  public void createOrder(Order order) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(OrderQueries.CREATE_ORDER_QUERY);
      preparedStatement.setLong(1, order.getAirplane().getId());
      preparedStatement.setLong(2, order.getUser().getId());
      preparedStatement.setString(3, order.getStatus().toString());
      if (preparedStatement.executeUpdate() <= 0) {
        log.warn("Cannot add order information.");
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
  public List<Order> getOrdersByUserID(long id) {
    List<Order> orderList = null;
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(OrderQueries.GET_ORDER_BY_USER_ID);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet != null) {
        orderList = orderShaper.shapeDataToList(resultSet);
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
    return orderList;
  }

  @Override
  public List<Order> getAllUnconfirmedOrders() {
    List<Order> orderList = new ArrayList<>();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement =
          connection.prepareStatement(OrderQueries.GET_ALL_UNCONFIRMED_ORDERS_QUERY);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet != null) {
        orderList = orderShaper.shapeDataToList(resultSet);
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
    return orderList;
  }

  @Override
  public int getBookedSeatsByAirplaneID(long id) {
    int result = 0;
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement =
          connection.prepareStatement(OrderQueries.GET_BOOKED_SEATS_BY_AIRPLANE_ID_QUERY);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        result = resultSet.getInt(1);
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
    return result;
  }

  @Override
  public void confirmOrderByID(long id) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(OrderQueries.CONFIRM_ORDER_BY_ID);
      preparedStatement.setLong(1, id);
      if (preparedStatement.executeUpdate() <= 0) {
        log.warn("Cannot update order information.");
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
  public void payForTheOrderByID(long id) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(OrderQueries.PAY_FOR_THE_ORDER_BY_ID);
      preparedStatement.setLong(1, id);
      if (preparedStatement.executeUpdate() <= 0) {

        log.warn("Cannot update order information.");
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
}
