package com.example.epamfinalproject.Database.Queries;

/** All Queries for User table */
public class UserQueries {

  private UserQueries() {}

  public static final String GET_USER_BY_ID_QUERY = "select * from users where users.id = ?";

  public static final String GET_USER_BY_LOGIN_QUERY = "select * from users where users.login = ?";

  public static final String GET_USER_BY_ROLE_CLIENT_QUERY =
      "select * from users where role = 'client'";

  public static final String GET_ALL_USERS_QUERY = "select * from users";

  public static final String REGISTER_USER_QUERY =
      "insert into users(first_name, last_name, login, password, role) values (?,?,?,?,?);";

  public static final String UPDATE_USER_PASSPORT_QUERY =
      "update users set passport_img = ? where id = ?";
}
