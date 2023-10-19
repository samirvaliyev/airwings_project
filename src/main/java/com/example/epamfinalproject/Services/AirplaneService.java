package com.example.epamfinalproject.Services;

import com.example.epamfinalproject.Database.Interfaces.AirplaneDAO;
import com.example.epamfinalproject.Entities.Airplane;
import java.util.List;

public class AirplaneService {

  private final AirplaneDAO airplaneDAO;

  public AirplaneService(AirplaneDAO airplaneDAO) {
    this.airplaneDAO = airplaneDAO;
  }

  public void createAirplane(Airplane airplane) {
    airplaneDAO.createAirplane(airplane);
  }

  public void updateAirplaneByID(Airplane airplane, long id) {
    airplaneDAO.updateAirplaneByID(airplane, id);
  }

  public void deleteAirplaneByID(long id) {
    airplaneDAO.deleteAirplaneByID(id);
  }

  public void confirmAirplaneByID(long id) {
    airplaneDAO.confirmAirplaneByID(id);
  }

  public List<Airplane> getAllAirplanesForPage(String query) {
    return airplaneDAO.getAllAirplanesForPage(query);
  }

  public int getNumberOfActualAirplanes(String query) {
    return airplaneDAO.getNumberOfActualAirplanes(query);
  }

  public Airplane getAirplaneByID(long id) {
    return airplaneDAO.getAirplaneByID(id);
  }
}
