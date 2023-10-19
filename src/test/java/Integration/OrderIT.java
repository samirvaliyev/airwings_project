package Integration;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.example.epamfinalproject.Database.Implementations.AirplaneImplementation;
import com.example.epamfinalproject.Database.Implementations.OrderImplementation;
import com.example.epamfinalproject.Database.Implementations.UserImplementation;
import com.example.epamfinalproject.Entities.*;
import com.example.epamfinalproject.Entities.Enums.Status;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.Order;
import com.example.epamfinalproject.Services.AirplaneService;
import com.example.epamfinalproject.Services.OrderService;
import com.example.epamfinalproject.Services.UserService;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderIT {
  private OrderService orderService;
  private static User user;
  private static Airplane airplane;

  @BeforeAll
  void beforeAll() {
    postgresContainer.start();

    UserService userService = new UserService(new UserImplementation());
    AirplaneService airplaneService = new AirplaneService(new AirplaneImplementation());
    orderService = new OrderService(new OrderImplementation());
    user =
        new User.UserBuilder()
            .id(1)
            .firstName("Name")
            .lastName("Name")
            .login("Login")
            .role(UserRole.CLIENT)
            .build();
    airplane =
        new Airplane(
            1,
            null,
            new Flight(1, "Name", 10),
            new Route(1, "Dep", "Dest", 10),
            0,
            false,
            false,
            LocalDate.parse("2022-12-12"),
            LocalDate.parse("2022-12-12"));

    userService.registerUser(user);
    airplaneService.createAirplane(airplane);
  }
  @Container
  private static final PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:11.1")
          .withExposedPorts(5432)
          .withCreateContainerCmdModifier(
              cmd ->
                  cmd.withHostConfig(
                      new HostConfig()
                          .withPortBindings(
                              new PortBinding(
                                  Ports.Binding.bindPort(1988), new ExposedPort(5432)))))
          .withDatabaseName("postgres")
          .withUsername("user")
          .withPassword("password")
          .withInitScript("init_script.sql");

  @Test
  void createOrderTest() {
    Order order = new Order(1, airplane, user, Status.PENDING);
    orderService.createOrder(order);

    assertThat(orderService.getAllUnconfirmedOrders()).hasSize(1);

    assertThat(orderService.getOrdersByUserID(1).get(0))
        .isEqualToComparingFieldByFieldRecursively(order);
  }

  @Test
  void payForOrderTest() {
    Order order = new Order(1, airplane, user, Status.PAID);
    orderService.createOrder(order);
    orderService.payForTheOrderByID(1);
    assertThat(orderService.getOrdersByUserID(1).get(0))
        .usingComparatorForFields((x, y) -> 0, "id")
        .isEqualToComparingFieldByFieldRecursively(order);
  }

  @Test
  void getBookedSeatsTest() {
    Order order = new Order(1, airplane, user, Status.PAID);
    orderService.createOrder(order);
    assertThat(
            orderService.getBookedSeatsByAirplaneID(orderService.getOrdersByUserID(1).get(0).getId()))
        .isEqualTo(2);
  }
  @Test
  void confirmOrderTest(){
    Order order = new Order(1, airplane, user, Status.CONFIRMED);
    orderService.createOrder(order);
    orderService.confirmOrderByID(1);
    List<Order> orderList = orderService.getOrdersByUserID(1);
    assertThat(orderList.get(orderList.size() - 1))
            .usingComparatorForFields((x, y) -> 0, "id")
            .isEqualToComparingFieldByFieldRecursively(order);
  }
  @AfterAll
  void close() {
    postgresContainer.close();
  }
}
