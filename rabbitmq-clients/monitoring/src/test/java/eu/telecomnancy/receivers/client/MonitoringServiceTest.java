package eu.telecomnancy.receivers.client;

import eu.telecomnancy.receivers.client.monitoring.services.MonitoringService;
import eu.telecomnancy.receivers.client.monitoring.services.counters.TeamCounterService;
import eu.telecomnancy.receivers.client.monitoring.services.counters.UserCounterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Unit test suite for the TeamService
 *
 * @see MonitoringService
 */
@ExtendWith(MockitoExtension.class)
public class MonitoringServiceTest {

    /**
     * Mocked team counter service to be injected for the unit tests
     */
    @Mock
    TeamCounterService teamCounterService;

    /**
     * Mocked user counter service to be injected for the unit tests
     */
    @Mock
    UserCounterService userCounterService;

    @Test
    public void givenTheCreateTeamCommand_WhenUpdatingTheCounter_ThenTheTeamCountShouldBeIncremented() {
        // Arrange
        doNothing()
                .when(teamCounterService)
                .increment();

        String receivedCommand = TeamCounterService.INCREMENT_COUNT_OPERATION_NAME;

        MonitoringService monitoringService = new MonitoringService(teamCounterService, userCounterService);

        // Act
        monitoringService.alterCountFromOperation(receivedCommand);

        // Assert
        verify(teamCounterService, times(1))
                .increment();
    }

    @Test
    public void givenTheCreateUserCommand_WhenUpdatingTheCounter_ThenTheUserCountShouldBeIncremented() {
        // Arrange
        doNothing()
                .when(userCounterService)
                .increment();

        String receivedCommand = UserCounterService.INCREMENT_COUNT_OPERATION_NAME;

        MonitoringService monitoringService = new MonitoringService(teamCounterService, userCounterService);

        // Act
        monitoringService.alterCountFromOperation(receivedCommand);

        // Assert
        verify(userCounterService, times(1))
                .increment();
    }

    @Test
    public void givenTheDeleteTeamCommand_WhenUpdatingTheCounter_ThenTheTeamCountShouldBeDecremented() {
        // Arrange
        doNothing()
                .when(teamCounterService)
                .decrement();

        String receivedCommand = TeamCounterService.DECREMENT_COUNT_OPERATION_NAME;

        MonitoringService monitoringService = new MonitoringService(teamCounterService, userCounterService);

        // Act
        monitoringService.alterCountFromOperation(receivedCommand);

        // Assert
        verify(teamCounterService, times(1))
                .decrement();
    }

    @Test
    public void givenTheDeleteUserCommand_WhenUpdatingTheCounter_ThenTheUserCountShouldBeDecremented() {
        // Arrange
        doNothing()
                .when(userCounterService)
                .decrement();

        String receivedCommand = UserCounterService.DECREMENT_COUNT_OPERATION_NAME;

        MonitoringService monitoringService = new MonitoringService(teamCounterService, userCounterService);

        // Act
        monitoringService.alterCountFromOperation(receivedCommand);

        // Assert
        verify(userCounterService, times(1))
                .decrement();
    }

}
