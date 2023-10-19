package com.example.epamfinalproject.Entities;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airplane {
  private long id;
  private String name;
  private Flight flight;
  private Route route;
  private int price;
  private boolean deleted = false;
  private boolean confirmed = false;
  LocalDate startOfTheAirplane;
  LocalDate endOfTheAirplane;

  /**
   * Constructor - creating new object with specific values
   *
   * @param flight Airplane flight
   * @param route The route the flight takes
   * @param name Airplane title
   * @param price Price for one seat on the flight
   * @param startOfTheAirplane Date of leaving
   * @param endOfTheAirplane Day of arriving at destination
   */
  public Airplane(
      Flight flight,
      Route route,
      String name,
      int price,
      LocalDate startOfTheAirplane,
      LocalDate endOfTheAirplane) {
    this.flight = flight;
    this.route = route;
    this.name = name;
    this.price = price;
    this.startOfTheAirplane = startOfTheAirplane;
    this.endOfTheAirplane = endOfTheAirplane;
  }
}
