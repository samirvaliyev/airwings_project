package com.example.epamfinalproject.Entities;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
  private long id;
  private String name;
  private int passengerCapacity;
  List<Staff> staff;

  /**
   * Constructor - creating new object with specific values
   *
   * @param name Name of the flight
   * @param passengerCapacity Total number of seats
   * @see Flight#Flight(long, String, int)
   */
  public Flight(String name, int passengerCapacity) {
    this.name = name;
    this.passengerCapacity = passengerCapacity;
  }

  /**
   * Constructor - creating new object with specific values
   *
   * @param id primary Key for Flight
   * @param name Name of the flight
   * @param passengerCapacity Total number of seats
   * @see Flight#Flight(String, int)
   */
  public Flight(long id, String name, int passengerCapacity) {
    this.id = id;
    this.name = name;
    this.passengerCapacity = passengerCapacity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Flight flight = (Flight) o;
    return this.getId() == flight.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, passengerCapacity);
  }
}
