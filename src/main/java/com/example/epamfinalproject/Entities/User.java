package com.example.epamfinalproject.Entities;

import com.example.epamfinalproject.Entities.Enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private long id;
  private String firstName;
  private String lastName;
  private String login;
  private String password;
  private UserRole role;
  private byte[] passport;

  /**
   * Constructor - creating new object with specific values The constructor was declared using the
   * Builder pattern
   *
   * @param builder constructor of builder pattern
   */
  private User(UserBuilder builder) {
    this.id = builder.id;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.login = builder.login;
    this.password = builder.password;
    this.role = builder.role;
    this.passport = builder.passport;
  }

  public static class UserBuilder {
    private long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private UserRole role;
    private byte[] passport;

    /**
     * {@summary Builder for new instances of {@link UserBuilder}.}
     *
     * <p>In particular, these are the attributes
     *
     * <ul>
     *   <li>{@link #id(long) Id}
     *   <li>{@link #firstName(String) First name}
     *   <li>{@link #lastName(String) Last name}
     *   <li>{@link #login(String) Login}
     *   <li>{@link #password(String) Password}
     *   <li>{@link #role(UserRole) User Role} see {@link UserRole}
     *   <li>{@link #passport(byte[]) Passport} Loads only if User has ever placed an order
     * </ul>
     */
    public UserBuilder() {}

    /**
     * Sets the id attribute for the new instance of {@link User}.
     *
     * @param id User id
     */
    public UserBuilder id(long id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the firstName attribute for the new instance of {@link User}.
     *
     * @param firstName User's first name
     */
    public UserBuilder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    /**
     * Sets the lastName attribute for the new instance of {@link User}.
     *
     * @param lastName User's last name
     */
    public UserBuilder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    /**
     * Sets the login attribute for the new instance of {@link User}.
     *
     * @param login User's login
     */
    public UserBuilder login(String login) {
      this.login = login;
      return this;
    }

    /**
     * Sets the password attribute for the new instance of {@link User}.
     *
     * @param password Encrypted User's password
     */
    public UserBuilder password(String password) {
      this.password = password;
      return this;
    }

    /**
     * Sets the role attribute for the new instance of {@link User}.
     *
     * @param role User's role
     * @see UserRole
     */
    public UserBuilder role(UserRole role) {
      this.role = role;
      return this;
    }

    /**
     * Sets the passport attribute for the new instance of {@link User}.
     *
     * @param passport Screen of User's documents. Loads only if User has ever placed an order
     */
    public UserBuilder passport(byte[] passport) {
      this.passport = passport;
      return this;
    }

    /**
     * Creates a new instance of {@link User}.
     *
     * @return The new instance.
     */
    public User build() {
      return new User(this);
    }
  }
}
