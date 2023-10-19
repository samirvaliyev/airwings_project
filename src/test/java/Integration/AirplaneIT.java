package Integration;

import com.example.epamfinalproject.Database.Implementations.AirplaneImplementation;
import com.example.epamfinalproject.Entities.Airplane;
import com.example.epamfinalproject.Entities.Route;
import com.example.epamfinalproject.Entities.Flight;
import com.example.epamfinalproject.Services.AirplaneService;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AirplaneIT {
    private AirplaneService airplaneService;
    private static Airplane airplane;

    @BeforeAll
    void beforeAll() {
        postgresContainer.start();
        airplaneService = new AirplaneService(new AirplaneImplementation());
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
    }
@BeforeEach
void beforeEach(){
        airplane.setDeleted(false);
        airplane.setConfirmed(false);
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
    void createAirplaneTest(){
        airplaneService.createAirplane(airplane);
        assertThat(airplaneService.getAirplaneByID(1)).isEqualToComparingFieldByFieldRecursively(airplane);

    }
    @Test
    void confirmAirplaneTest(){
        airplaneService.createAirplane(airplane);
        airplaneService.confirmAirplaneByID(1);
        airplane.setConfirmed(true);
        assertThat(airplaneService.getAirplaneByID(1))
                .usingComparatorForFields((x, y) -> 0, "deleted")
                .isEqualToComparingFieldByFieldRecursively(airplane);
    }
    @Test
    void updateAirplaneTest(){
        airplane.setDeleted(false);
        airplaneService.createAirplane(airplane);
        airplane.setConfirmed(false);
        airplane.setPrice(200);
        airplaneService.updateAirplaneByID(airplane,1);
        assertThat(airplaneService.getAirplaneByID(1))
                .usingComparatorForFields((x, y) -> 0, "deleted")
                .isEqualToComparingFieldByFieldRecursively(airplane);    }
    @Test
    void deleteAirplaneTest(){
        airplaneService.createAirplane(airplane);
        airplaneService.deleteAirplaneByID(1);
        assertTrue(airplaneService.getAirplaneByID(1).isDeleted());
    }
}
