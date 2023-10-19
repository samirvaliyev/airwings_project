package com.example.epamfinalproject.Database.Shaper;

import com.example.epamfinalproject.Database.Implementations.StaffImplementation;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Enums.Status;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.Order;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.StaffService;
import com.example.epamfinalproject.Utility.FieldKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderShaper implements DataShaper<Order> {

  DataShaper<Airplane> airplaneShaper = new AirplaneShaper(new StaffService(new StaffImplementation()));

  /**
   * @param resultSet result of SQL query execution
   * @return new instance of Order class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public Order shapeData(ResultSet resultSet) throws SQLException {
    Order order = new Order();
    Airplane airplane = airplaneShaper.shapeData(resultSet);
    airplane.setId(resultSet.getLong(FieldKey.ORDER_AIRPLANE_ID));

    order.setId(resultSet.getLong(FieldKey.ENTITY_ID));
    order.setAirplane(airplane);
    order.setUser(userShaper(resultSet));
    order.setStatus(Status.fromString(resultSet.getString(FieldKey.ORDER_STATUS)));
    return order;
  }

  /**
   * @param resultSet result of SQL query execution
   * @return list filled with new instances of Order class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public List<Order> shapeDataToList(ResultSet resultSet) throws SQLException {
    List<Order> orderList = new ArrayList<>();
    while (resultSet.next()) {
      Order order = new Order();
      order.setId(resultSet.getLong(FieldKey.ENTITY_ID));
      Airplane airplane = airplaneShaper.shapeData(resultSet);
      airplane.setId(resultSet.getLong(FieldKey.ORDER_AIRPLANE_ID));
      order.setAirplane(airplane);
      order.setUser(userShaper(resultSet));
      order.setStatus(Status.fromString(resultSet.getString(FieldKey.ORDER_STATUS)));
      orderList.add(order);
    }
    return orderList;
  }

  /**
   * Helper function for forming an instance of the User class.
   *
   * @param resultSet result of SQL query execution
   * @return new instance of User class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  public User userShaper(ResultSet resultSet) throws SQLException {
    return new User.UserBuilder()
        .id(resultSet.getLong(FieldKey.ORDER_USER_ID))
        .firstName(resultSet.getString(FieldKey.FIRST_NAME))
        .lastName(resultSet.getString(FieldKey.LAST_NAME))
        .login(resultSet.getString(FieldKey.LOGIN))
        .password(resultSet.getString(FieldKey.PASSWORD))
        .role(UserRole.fromString(resultSet.getString(FieldKey.ROLE)))
        .build();
  }
}
