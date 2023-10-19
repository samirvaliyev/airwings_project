package com.example.epamfinalproject.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route {
  private long id;
  private String departure;
  private String destination;
  private int transitTime;

  /**
   * Constructor - creating new object with specific values
   *
   * @param departure Place, where airplane started
   * @param destination Final port
   * @param transitTime Total time on the flight's board
   */
  public Route(String departure, String destination, int transitTime) {
    this.departure = departure;
    this.destination = destination;
    this.transitTime = transitTime;
  }
}
