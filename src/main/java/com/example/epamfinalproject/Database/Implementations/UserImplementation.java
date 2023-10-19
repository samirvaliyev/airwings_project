package com.example.epamfinalproject.Database.Implementations;

import com.example.epamfinalproject.Database.HikariConnectionPool;
import com.example.epamfinalproject.Database.Interfaces.UserDAO;
import com.example.epamfinalproject.Database.Queries.UserQueries;
import com.example.epamfinalproject.Database.Shaper.DataShaper;
import com.example.epamfinalproject.Database.Shaper.UserShaper;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Utility.Constants;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class UserImplementation implements UserDAO {
  private static final Logger log = Logger.getLogger(UserImplementation.class.getName());
  private PreparedStatement preparedStatement;
  DataShaper<User> userShaper = new UserShaper();

  @Override
  public void registerUser(User user) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(UserQueries.REGISTER_USER_QUERY);
      preparedStatement.setString(1, user.getFirstName());
      preparedStatement.setString(2, user.getLastName());
      preparedStatement.setString(3, user.getLogin());
      preparedStatement.setString(4, user.getPassword());
      preparedStatement.setString(5, user.getRole().toString());
      if (preparedStatement.executeUpdate() <= 0) {
        log.warn("Cannot register user.");
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
  public User getUserByLogin(String login) {
    User user = null;
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(UserQueries.GET_USER_BY_LOGIN_QUERY);
      preparedStatement.setString(1, login);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = userShaper.shapeData(resultSet);
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
    return user;
  }

  @Override
  public List<User> getClientUsers() {
    List<User> users = new ArrayList<>();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(UserQueries.GET_USER_BY_ROLE_CLIENT_QUERY);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet != null) {
        users = userShaper.shapeDataToList(resultSet);
      }
      log.info("List of Passengers created and filled with " + users.size() + " users");
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
    return users;
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(UserQueries.GET_ALL_USERS_QUERY);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet != null) {
        users = userShaper.shapeDataToList(resultSet);
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
    return users;
  }

  @Override
  public User getUserByID(long id) {
    User user = null;
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(UserQueries.GET_USER_BY_ID_QUERY);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = userShaper.shapeData(resultSet);
      }
      log.info("User was found");
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
    return user;
  }

  @Override
  public void updateUserPassport(long id, InputStream image, long length) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      connection.setAutoCommit(false);
      preparedStatement = connection.prepareStatement(UserQueries.UPDATE_USER_PASSPORT_QUERY);
      preparedStatement.setBinaryStream(1, image, (int) length);
      preparedStatement.setLong(2, id);
      if (preparedStatement.executeUpdate() <= 0) {
        connection.rollback();
        log.warn("Error while committing. Image won't be updated");
      }
      connection.commit();
      connection.setAutoCommit(true);
      log.info("All changes committed");
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
