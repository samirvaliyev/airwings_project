package com.example.epamfinalproject.Services;

import com.example.epamfinalproject.Database.Interfaces.StaffDAO;
import com.example.epamfinalproject.Entities.Staff;
import java.util.List;

public class StaffService {

  private final StaffDAO staffDAO;

  public StaffService(StaffDAO staffDAO) {
    this.staffDAO = staffDAO;
  }

  public void registerStaff(Staff staff) {
    staffDAO.registerStaff(staff);
  }

  public List<Staff> getStaffByFlightID(long id) {
    return staffDAO.getAllStaffByFlightID(id);
  }
}
