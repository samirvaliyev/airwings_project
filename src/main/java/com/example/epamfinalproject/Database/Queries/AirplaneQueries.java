package com.example.epamfinalproject.Database.Queries;

import com.example.epamfinalproject.Utility.Constants;

/** All Queries for Airplane Table */
public class AirplaneQueries {

  private AirplaneQueries() {}

  public static final String CONFIRM_AIRPLANE_BY_ID_QUERY =
      "update airplanes set confirmed = true where id = ?";

  public static final String CREATE_AIRPLANE_QUERY =
      "insert into airplanes(flight_id, route_id,airplane_name, price, start_date,end_date) "
          + "values (?,?,?,?,?,?)";

  public static final String UPDATE_AIRPLANE_BY_ID_QUERY =
      "update airplanes set flight_id =?, route_id=?,airplane_name=?, price = ?, start_date=?,end_date=?"
          + " where id = ?";

  public static final String GET_AIRPLANE_BY_ID =
      "select * from airplanes "
          + "inner join routes r on r.id = airplanes.route_id "
          + "inner join flights s on s.id = airplanes.flight_id "
          + " where airplanes.id = ?";

  public static final String GET_AIRPLANE_BY_FLIGHT_ID =
      "select * from airplanes "
          + "inner join routes r on r.id = airplanes.route_id "
          + "inner join flights s on s.id = airplanes.flight_id "
          + " where airplanes.flight_id = ?";

  public static final String GET_ALL_ACTUAL_AIRPLANES_FOR_FIRST_PAGE_QUERY =
      "select * from airplanes "
          + "inner join routes r on r.id = airplanes.route_id "
          + "inner join flights s on s.id = airplanes.flight_id "
          + " where start_date > now()"
          + " and deleted = false "
          + "limit "
          + Constants.PAGE_SIZE
          + " offset "
          + 0;

  public static final String GET_ALL_ACTUAL_AIRPLANES_FOR_FIRST_PAGE_ADMIN_QUERY =
      "select * from airplanes "
          + "inner join routes r on r.id = airplanes.route_id "
          + "inner join flights s on s.id = airplanes.flight_id "
          + " where start_date > now()"
          + " and deleted = false "
          + " and confirmed = false "
          + "limit "
          + Constants.PAGE_SIZE
          + " offset "
          + 0;

  public static final String GET_ALL_AIRPLANES_FOR_FIRST_PAGE_QUERY =
      "select * from airplanes "
          + "inner join routes r on r.id = airplanes.route_id "
          + "inner join flights s on s.id = airplanes.flight_id "
          + "limit "
          + Constants.PAGE_SIZE
          + " offset "
          + 0;

  public static final String GET_ALL_ACTUAL_AIRPLANES_QUERY =
      "select * from airplanes "
          + "inner join routes r on r.id = airplanes.route_id "
          + "inner join flights s on s.id = airplanes.flight_id "
          + "where start_date >= now() and deleted = false";

  public static final String DELETE_AIRPLANE_BY_ID_QUERY =
      "update airplanes set deleted = true where id = ?";
}
