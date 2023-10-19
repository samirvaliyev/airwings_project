package com.example.epamfinalproject.Database.Interfaces;

import com.example.epamfinalproject.Entities.User;
import java.io.InputStream;
import java.util.List;

public interface UserDAO {

  void registerUser(User user);

  void updateUserPassport(long id, InputStream image, long length);

  User getUserByLogin(String login);

  User getUserByID(long id);

  List<User> getClientUsers();

  List<User> getAllUsers();
}
