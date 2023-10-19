package com.example.epamfinalproject.Utility;

/** The class stores patterns for validating fields that the user enters */
public class Regex {

  private Regex() {}

  // User SignUpCommand Regex
  public static final String USER_LOGIN = "^[A-Za-z0-9_]{4,}+$";
  public static final String USER_FIRSTNAME = "^[A-Z][a-zA-Z]{2,}+$";
  public static final String USER_LASTNAME = "^[A-Z][a-zA-Z]{2,}+$";

  // Flight CreateCommand Regex
  public static final String FLIGHT_NAME = "^[A-ZЄІЇА-Я][a-zа-яёєіїЄІЇА-Я\\s-]+[a-zа-яёєіїA-ZЄІЇА-Я]*$";

  // Route CreateCommand Regex
  public static final String ROUTE_DEPARTURE = "^[A-ZЄІЇА-Я][a-zа-яёєіїЄІЇА-Я\\s-]+[a-zа-яёєіїA-ZЄІЇА-Я]*$";
  public static final String ROUTE_DESTINATION = "^[A-ZЄІЇА-Я][a-zа-яёєіїЄІЇА-Я\\s-]+[a-zа-яёєіїA-ZЄІЇА-Я]*$";

  // Airplane CreateCommand Regex
  public static final String AIRPLANE_NAME = "^[A-ZЄІЇА-Я][a-zа-яёєіїЄІЇА-Я\\s-]+[a-zа-яёєіїA-ZЄІЇА-Я]*$";
}
