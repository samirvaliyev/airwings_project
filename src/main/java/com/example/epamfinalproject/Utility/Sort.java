package com.example.epamfinalproject.Utility;

import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Order;
import java.util.Comparator;
import java.util.List;

public class Sort {
  private Sort() {}

  public static List<Order> sortOrderByStatus(List<Order> orderList) {
    orderList.sort(Comparator.comparing(Order::getStatus));
    return orderList;
  }

  public static List<Airplane> sortAirplanesByStartDate(List<Airplane> airplaneList) {
    airplaneList.sort(Comparator.comparing(Airplane::getStartOfTheAirplane));
    return airplaneList;
  }
}
