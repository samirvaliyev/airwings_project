package com.example.epamfinalproject.Database.Shaper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Current Interface helps to collect data from ResultSet and fit it into Class Object.
 *
 * <p>{@link #shapeData(ResultSet)} function implementations are not supposed to move cursor if the
 * resultSet using resultSet.next() method, but only extract data from current row.
 *
 * <p>{@link #shapeDataToList(ResultSet)} implementations set the cursor itself, so the function
 * call should happen without first calling resultSet.next() method. Return list of elements
 * containing new instances of the class filled with the date from the resultSet
 */
public interface DataShaper<T> {

  /**
   * @param resultSet result of SQL query execution
   * @return new filled instance of class T
   * @throws SQLException if param is empty or some field does not exist
   */
  T shapeData(ResultSet resultSet) throws SQLException;

  /**
   * @param resultSet result of SQL query execution
   * @return list filled with new class instances
   * @throws SQLException if param is empty or some field does not exist
   */
  List<T> shapeDataToList(ResultSet resultSet) throws SQLException;
}
