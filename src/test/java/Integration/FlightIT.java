package Integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.epamfinalproject.Database.Implementations.FlightImplementation;
import com.example.epamfinalproject.Entities.Flight;
import com.example.epamfinalproject.Services.FlightService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightIT {

  private FlightService flightService;

  @BeforeAll
  void beforeAll() {
    postgresContainer.start();

    flightService = new FlightService(new FlightImplementation());
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
                                  Ports.Binding.bindPort(1988),
                                  new ExposedPort(5432)))))
          .withDatabaseName("postgres")
          .withUsername("user")
          .withPassword("password")
          .withInitScript("init_script.sql");

  @Test
  void registerFlightTest() {
    Flight currentFlight = new Flight(1, "Name", 12);
    flightService.registerFlight(currentFlight);
    Flight flight = flightService.getFlightByName("Name");
    assertTrue(new ReflectionEquals(currentFlight, "id", "staff").matches(flight));
  }

  @Test
  void updateFlightByIDTest() {
    Flight currentFlight = new Flight(1, "Flight", 25);
    flightService.updateFlightByID(currentFlight, 1);
    Flight flight = flightService.getFlightByName("Flight");
    assertTrue(new ReflectionEquals(currentFlight, "id", "staff").matches(flight));
  }

  @AfterAll
  static void close() {
    postgresContainer.close();
  }

  Stream<Arguments> flightStream() {
    List<Flight> flightList = new ArrayList<>();
    flightList.add(new Flight(1, "Donna", 100));
    flightList.add(new Flight(2, "Eola", 200));
    flightList.add(new Flight(3, "Elisabeth", 300));
    flightList.add(new Flight(4, "Elena", 400));
    return Stream.of(Arguments.of(flightList));
  }
}
