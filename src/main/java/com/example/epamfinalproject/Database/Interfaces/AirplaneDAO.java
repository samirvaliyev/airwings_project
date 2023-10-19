package com.example.epamfinalproject.Database.Interfaces;

import com.example.epamfinalproject.Entities.Airplane;

import java.util.List;

public interface AirplaneDAO {
  void createAirplane(Airplane airplane);

  void updateAirplaneByID(Airplane airplane, long id);

  void deleteAirplaneByID(long id);

  void confirmAirplaneByID(long id);

  int getNumberOfActualAirplanes(String query);

  Airplane getAirplaneByID(long id);

  List<Airplane> getAllAirplanesForPage(String query);

}
