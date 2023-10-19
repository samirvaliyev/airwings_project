package Integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.epamfinalproject.Database.Implementations.UserImplementation;
import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;
import com.example.epamfinalproject.Services.UserService;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserIT {
  private UserService userService;

  @BeforeAll
  void beforeAll() {
    postgresContainer.start();

    userService = new UserService(new UserImplementation());
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

  @ParameterizedTest
  @MethodSource("userStream")
  void userTest(User user, int size, int clientSize) {
    userService.registerUser(user);

    assertThat(userService.getUserByID(user.getId()))
        .isEqualToComparingFieldByFieldRecursively(user);

    assertThat(userService.getUserByLogin(user.getLogin()))
        .isEqualToComparingFieldByFieldRecursively(user);

    assertThat(userService.getAllUsers()).hasSize(size);

    assertThat(userService.getClientUsers()).hasSize(clientSize);
  }

  @AfterAll
  static void close() {
    postgresContainer.close();
  }

  Stream<Arguments> userStream() {
    return Stream.of(
        Arguments.of(
            new User.UserBuilder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .login("Ivan_ivanov")
                .password("password1")
                .role(UserRole.CLIENT)
                .build(),
            1,
            1),
        Arguments.of(
            new User.UserBuilder()
                .id(2)
                .firstName("Olga")
                .lastName("Ivanova")
                .login("Olga_ivanova")
                .password("password2")
                .role(UserRole.CLIENT)
                .build(),
            2,
            2),
        Arguments.of(
            new User.UserBuilder()
                .id(3)
                .firstName("Olga")
                .lastName("Petrova")
                .login("Olga_petrovaa")
                .password("password3")
                .role(UserRole.ADMINISTRATOR)
                .build(),
            3,
            2),
        Arguments.of(
            new User.UserBuilder()
                .id(4)
                .firstName("Elena")
                .lastName("Ivanova")
                .login("Elena_ivanova")
                .password("password4")
                .role(UserRole.CLIENT)
                .build(),
            4,
            3));
  }
}
