package com.example.epamfinalproject.Database.Shaper;

import com.example.epamfinalproject.Entities.Staff;
import com.example.epamfinalproject.Utility.FieldKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffShaper implements DataShaper<Staff> {
  /**
   * @param resultSet result of SQL query execution
   * @return new instance of Staff class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public Staff shapeData(ResultSet resultSet) throws SQLException {
    Staff staff = new Staff();
    staff.setId(resultSet.getLong(FieldKey.ENTITY_ID));
    staff.setFirstName(resultSet.getString(FieldKey.FIRST_NAME));
    staff.setLastName(resultSet.getString(FieldKey.LAST_NAME));
    staff.setFlightId(resultSet.getLong(FieldKey.AIRPLANE_FLIGHT_ID));
    return staff;
  }

  /**
   * @param resultSet result of SQL query execution
   * @return list filled with new instances of Staff class filled with resultSet data
   * @throws SQLException if param is empty or some field does not exist
   */
  @Override
  public List<Staff> shapeDataToList(ResultSet resultSet) throws SQLException {
    List<Staff> staffList = new ArrayList<>();
    while (resultSet.next()) {
      Staff staff = new Staff();
      staff.setId(resultSet.getLong(FieldKey.ENTITY_ID));
      staff.setFirstName(resultSet.getString(FieldKey.FIRST_NAME));
      staff.setLastName(resultSet.getString(FieldKey.LAST_NAME));
      staff.setFlightId(resultSet.getLong(FieldKey.AIRPLANE_FLIGHT_ID));
      staffList.add(staff);
    }
    return staffList;
  }
}
