package com.example.epamfinalproject.Database.Implementations;

import com.example.epamfinalproject.Database.HikariConnectionPool;
import com.example.epamfinalproject.Database.Interfaces.StaffDAO;
import com.example.epamfinalproject.Database.Queries.StaffQueries;
import com.example.epamfinalproject.Database.Shaper.DataShaper;
import com.example.epamfinalproject.Database.Shaper.StaffShaper;
import com.example.epamfinalproject.Entities.Staff;
import com.example.epamfinalproject.Utility.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class StaffImplementation implements StaffDAO {
  private static final Logger log = Logger.getLogger(StaffImplementation.class.getName());
  private PreparedStatement preparedStatement;
  DataShaper<Staff> staffShaper = new StaffShaper();

  @Override
  public void registerStaff(Staff staff) {
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(StaffQueries.REGISTER_STAFF_QUERY);
      preparedStatement.setString(1, staff.getFirstName());
      preparedStatement.setString(2, staff.getLastName());
      preparedStatement.setLong(3, staff.getFlightId());
      if (preparedStatement.executeUpdate() <= 0) {
        log.warn("Cannot register staff.");
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
  public List<Staff> getAllStaffByFlightID(long id) {
    List<Staff> staffList = new ArrayList<>();
    try (Connection connection = HikariConnectionPool.getConnection()) {
      preparedStatement = connection.prepareStatement(StaffQueries.GET_STAFF_BY_FLIGHT_ID_QUERY);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet != null) {
        staffList = staffShaper.shapeDataToList(resultSet);
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
    return staffList;
  }
}
