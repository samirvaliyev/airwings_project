package com.example.epamfinalproject.Entities.Enums;

public enum UserRole {
  CLIENT("client"),
  ADMINISTRATOR("administrator");

  private final String text;

  UserRole(String text) {
    this.text = text;
  }

  /**
   * Function convert parameter text to instance of {@link UserRole}
   *
   * @param text String value
   * @return instance of {@link UserRole}
   */
  public static UserRole fromString(String text) {
    for (UserRole b : UserRole.values()) {
      if (b.text.equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }

  /**
   * @return text value of {@link UserRole} instance
   */
  @Override
  public String toString() {
    return text;
  }
}
