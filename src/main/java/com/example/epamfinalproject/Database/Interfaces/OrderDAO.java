package com.example.epamfinalproject.Database.Interfaces;

import com.example.epamfinalproject.Entities.Order;
import java.util.List;

public interface OrderDAO {

  void createOrder(Order order);

  List<Order> getOrdersByUserID(long id);

  List<Order> getAllUnconfirmedOrders();

  int getBookedSeatsByAirplaneID(long id);

  void confirmOrderByID(long id);

  void payForTheOrderByID(long id);
}
