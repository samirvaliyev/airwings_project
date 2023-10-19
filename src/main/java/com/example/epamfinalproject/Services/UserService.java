package com.example.epamfinalproject.Services;

import com.example.epamfinalproject.Database.Interfaces.UserDAO;
import com.example.epamfinalproject.Entities.User;
import java.io.InputStream;
import java.util.List;

public class UserService {

  private final UserDAO userDAO;

  public UserService(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  public void registerUser(User user) {
    userDAO.registerUser(user);
  }

  public User getUserByID(long id) {
    return userDAO.getUserByID(id);
  }

  public List<User> getAllUsers() {
    return userDAO.getAllUsers();
  }

  public List<User> getClientUsers() {
    return userDAO.getClientUsers();
  }

  public User getUserByLogin(String login) {
    return userDAO.getUserByLogin(login);
  }

  public void updateUserPassport(long id, InputStream image, long length) {
    userDAO.updateUserPassport(id, image, length);
  }
}
