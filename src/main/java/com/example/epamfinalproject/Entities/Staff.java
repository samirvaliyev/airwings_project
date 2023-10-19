package com.example.epamfinalproject.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
  private long id;
  private String firstName;
  private String lastName;
  private long flightId;

  /**
   * Constructor - creating new object with specific values
   *
   * @param firstName First name of staff
   * @param lastName Last name of staff
   * @param flightId ID of the flight on which the staff is listed
   */
  public Staff(String firstName, String lastName, long flightId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.flightId = flightId;
  }
}
