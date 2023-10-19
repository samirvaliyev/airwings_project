package com.example.epamfinalproject.Utility;

/**
 * Defines fields names
 *
 * <p>These names are used as rows for Database tables, and as entities parameters on JSP pages
 */
public class FieldKey {

  private FieldKey() {}

  // User and Staff table fields
  public static final String ENTITY_ID = "id";
  public static final String FIRST_NAME = "first_name";
  public static final String LAST_NAME = "last_name";

  // User fields
  public static final String LOGIN = "login";
  public static final String PASSWORD = "password";
  public static final String CONFIRM_PASSWORD = "confirmPassword";
  public static final String ROLE = "role";
  public static final String PASSPORT = "passport_img";

  // Flights table field
  public static final String FLIGHT_NAME = "name";
  public static final String PASSENGER_CAPACITY = "passenger_capacity";

  // Route table fields
  public static final String DEPARTURE = "departure";
  public static final String DESTINATION = "destination";
  public static final String TRANSIT_TIME = "transit_time";

  // Order table fields
  public static final String ORDER_AIRPLANE_ID = "airplane_id";
  public static final String ORDER_USER_ID = "user_id";
  public static final String ORDER_STATUS = "status";

  // Airplane table fields
  public static final String AIRPLANE_FLIGHT_ID = "flight_id";
  public static final String AIRPLANE_ROUTE_ID = "route_id";
  public static final String AIRPLANE_NAME = "airplane_name";
  public static final String AIRPLANE_FLIGHT_NAME = "flight_name";
  public static final String AIRPLANE_PRICE = "price";
  public static final String AIRPLANE_DELETED = "deleted";
  public static final String AIRPLANE_CONFIRMED = "confirmed";
  public static final String AIRPLANE_LEAVING = "start_date";
  public static final String AIRPLANE_ARRIVING = "end_date";
}
