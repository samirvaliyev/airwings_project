package com.example.epamfinalproject.Services;

import com.example.epamfinalproject.Database.Interfaces.OrderDAO;
import com.example.epamfinalproject.Entities.Order;
import java.util.List;

public class OrderService {

  private final OrderDAO orderDAO;

  public OrderService(OrderDAO orderDAO) {
    this.orderDAO = orderDAO;
  }

  public void createOrder(Order order) {
    orderDAO.createOrder(order);
  }

  public List<Order> getAllUnconfirmedOrders() {
    return orderDAO.getAllUnconfirmedOrders();
  }

  public List<Order> getOrdersByUserID(long id) {
    return orderDAO.getOrdersByUserID(id);
  }

  public int getBookedSeatsByAirplaneID(long id) {
    return orderDAO.getBookedSeatsByAirplaneID(id);
  }

  public void confirmOrderByID(long id) {
    orderDAO.confirmOrderByID(id);
  }
  public void payForTheOrderByID(long id) {
    orderDAO.payForTheOrderByID(id);
  }
}
