package com.example.epamfinalproject.Database.Queries;

/** All Queries for Route Table */
public class RouteQueries {

  private RouteQueries() {}

  public static final String CREATE_ROUTE_QUERY =
      "insert into routes(departure, destination, transit_time) values (?,?,?)";

public static final String CREATE_ROUTE_QUERY_RETURNING_ID =
      "insert into routes(departure, destination, transit_time) values (?,?,?) returning id";

  public static final String UPDATE_ROUTE_QUERY =
      "update routes set departure = ?,destination = ?,transit_time= ? where id = ?";

  public static final String GET_ROUTE_BY_ID_QUERY = "select * from routes where id = ?";

  public static final String GET_ALL_ROUTES_QUERY = "select * from routes";

  public static final String GET_ROUTE_BY_ALL_PARAMETERS_QUERY =
      "select  * from routes where departure = ? and destination = ? and transit_time = ?";
}
