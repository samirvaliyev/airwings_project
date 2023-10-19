package com.example.epamfinalproject.Database.Queries;

/** Queries for Flight table */
public class FlightQueries {

  private FlightQueries() {}

  public static final String REGISTER_FLIGHT_QUERY =
      "insert into flights(name,passenger_capacity) values (?,?)";

  public static final String REGISTER_FLIGHT_QUERY_RETURNING_ID =
      "insert into flights(name,passenger_capacity) values (?,?) returning id";

  public static final String GET_FLIGHT_BY_NAME_QUERY = "select * from flights where name = ?";

  public static final String UPDATE_FLIGHT_BY_ID_QUERY =
      "update flights set name=?,passenger_capacity=? where id = ?";

  public static final String GET_ALL_FLIGHTS_QUERY = "select * from flights";
}
