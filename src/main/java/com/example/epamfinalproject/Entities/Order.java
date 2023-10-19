package com.example.epamfinalproject.Entities;

import com.example.epamfinalproject.Entities.Enums.Status;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private long id;
  private Airplane airplane;
  private User user;
  private Status status = Status.PENDING;

  /**
   * Constructor - creating new object with specific values
   *
   * @param airplane Airplane on which seats are purchased
   * @param user Client, who bought airplane
   */
  public Order(Airplane airplane, User user) {
    this.airplane = airplane;
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return airplane.getId() == order.airplane.getId() && user.getId() == order.user.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(airplane, user);
  }
}
