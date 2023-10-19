package com.example.epamfinalproject.Services;

import com.example.epamfinalproject.Database.Interfaces.FlightDAO;
import com.example.epamfinalproject.Entities.Flight;

import java.util.List;

public class FlightService {

  private final FlightDAO flightDAO;

  public FlightService(FlightDAO flightDAO) {
    this.flightDAO = flightDAO;
  }

  public void registerFlight(Flight flight) {
    flightDAO.registerFlight(flight);
  }

  public void updateFlightByID(Flight flight, long id) {
    flightDAO.updateFlightByID(flight, id);
  }

  public Flight getFlightByName(String name) {
    return flightDAO.getFlightByName(name);
  }

  public List<Flight> getAllFlights() {
    return flightDAO.getAllFlights();
  }
}
