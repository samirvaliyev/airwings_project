package com.example.epamfinalproject.Database.Shaper;

import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Utility.FieldKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserShaper implements DataShaper<User> {
  /**
   * @param resultSet result of SQL query execution
   * @return new instance of User class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public User shapeData(ResultSet resultSet) throws SQLException {

    return new User.UserBuilder()
        .id(resultSet.getLong(FieldKey.ENTITY_ID))
        .firstName(resultSet.getString(FieldKey.FIRST_NAME))
        .lastName(resultSet.getString(FieldKey.LAST_NAME))
        .login(resultSet.getString(FieldKey.LOGIN))
        .password(resultSet.getString(FieldKey.PASSWORD))
        .role(UserRole.fromString(resultSet.getString(FieldKey.ROLE)))
        .passport(resultSet.getBytes(FieldKey.PASSPORT))
        .build();
  }

  /**
   * @param resultSet result of SQL query execution
   * @return list filled with new instances of User class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public List<User> shapeDataToList(ResultSet resultSet) throws SQLException {
    List<User> userList = new ArrayList<>();
    User user;
    while (resultSet.next()) {
      user =
          new User.UserBuilder()
              .id(resultSet.getLong(FieldKey.ENTITY_ID))
              .firstName(resultSet.getString(FieldKey.FIRST_NAME))
              .lastName(resultSet.getString(FieldKey.LAST_NAME))
              .login(resultSet.getString(FieldKey.LOGIN))
              .password(resultSet.getString(FieldKey.PASSWORD))
              .role(UserRole.fromString(resultSet.getString(FieldKey.ROLE)))
              .passport(resultSet.getBytes(FieldKey.PASSPORT))
              .build();
      userList.add(user);
    }
    return userList;
  }
}
