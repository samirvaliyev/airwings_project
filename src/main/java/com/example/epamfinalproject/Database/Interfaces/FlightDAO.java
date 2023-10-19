package com.example.epamfinalproject.Database.Interfaces;

import com.example.epamfinalproject.Entities.Flight;

import java.util.List;

public interface FlightDAO {

  void registerFlight(Flight flight);

  void updateFlightByID(Flight flight, long id);

  Flight getFlightByName(String name);

  List<Flight> getAllFlights();
}
