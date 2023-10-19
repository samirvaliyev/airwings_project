package com.example.epamfinalproject.Entities.Enums;

public enum Status {
  PENDING("pending"),
  PAID("paid"),
  CONFIRMED("confirmed");

  private final String text;

  Status(String text) {
    this.text = text;
  }

  /**
   * Function convert parameter text to instance of {@link Status}
   *
   * @param text String value
   * @return instance of {@link Status}
   */
  public static Status fromString(String text) {
    for (Status b : Status.values()) {
      if (b.text.equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }

  /**
   * @return text value of {@link Status} instance
   */
  @Override
  public String toString() {
    return text;
  }
}
