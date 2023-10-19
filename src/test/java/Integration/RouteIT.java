package Integration;

import static org.assertj.core.api.Assertions.*;

import com.example.epamfinalproject.Database.Implementations.RouteImplementation;
import com.example.epamfinalproject.Entities.Route;
import com.example.epamfinalproject.Services.RouteService;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/** All tests were written using Assertj library */
class RouteIT {
  private RouteService routeService;

  @BeforeAll
  void beforeAll() {
    postgresContainer.start();
    routeService = new RouteService(new RouteImplementation());
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
  void createRouteTest() {
    Route route = new Route(1, "Departure", "Destination", 10);
    routeService.createRoute(route);
    assertThat(route).isEqualToComparingFieldByFieldRecursively(routeService.getRouteByID(1));
  }

  @Test
  void getAllRoutesTest() {
    Route route = new Route(2, "Departure", "Destination", 5);
    routeService.createRoute(route);
    List<Route> routes = routeService.getAllRoutes();
    assertThat(routes).doesNotContain(new Route())
                      .isNotEmpty()
                      .isNotNull();
  }

  @Test
  void getRouteByParamsTest() {
    Route route = new Route("Departure", "Destination", 5);
    Route routeByParams = routeService.getRouteByAllParameters(route);
    assertThat(routeByParams).isNotNull();
  }

  @Test
  void updateRouteTest() {
    Route route = new Route("Departure","Departure",0);
    Route updatedRoute = new Route( "Departure", "Destination", 10);
    routeService.createRoute(route);
    long id = routeService.getRouteByAllParameters(route).getId();
    routeService.updateRouteByID(updatedRoute,id);
    assertThat(routeService.getRouteByID(id)).isEqualToIgnoringGivenFields(updatedRoute,"id");
  }
  @AfterAll
  static void close() {
    postgresContainer.close();
  }
}
