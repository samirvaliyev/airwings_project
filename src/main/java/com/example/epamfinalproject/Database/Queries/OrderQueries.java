package com.example.epamfinalproject.Database.Queries;

/** All Queries for Order Table */
public class OrderQueries {

  private OrderQueries() {}

  public static final String CREATE_ORDER_QUERY =
      "insert into orders(airplane_id, user_id, status) values (?,?,?)";

  public static final String GET_ORDER_BY_USER_ID =
      "select * from orders"
          + " inner join airplanes c on c.id = orders.airplane_id  "
          + "inner join routes r on r.id = c.route_id "
          + "inner join flights s on s.id = c.flight_id"
          + " inner join users u on u.id = orders.user_id  "
          + "where user_id = ?";

  public static final String GET_ALL_UNCONFIRMED_ORDERS_QUERY =
      "select * from orders"
          + " inner join airplanes c on c.id = orders.airplane_id  "
          + "inner join routes r on r.id = c.route_id "
          + "inner join flights s on s.id = c.flight_id"
          + " inner join users u on u.id = orders.user_id where orders.status = 'pending'";

  public static final String GET_BOOKED_SEATS_BY_AIRPLANE_ID_QUERY =
      "select count(*) from orders where airplane_id = ?";

  public static final String CONFIRM_ORDER_BY_ID = "update orders set status = 'confirmed' where id = ?";
  public static final String PAY_FOR_THE_ORDER_BY_ID = "update orders set status = 'paid' where id = ?";
}
