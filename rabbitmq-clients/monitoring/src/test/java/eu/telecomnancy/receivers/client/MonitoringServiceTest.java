package eu.telecomnancy.receivers.client;

import eu.telecomnancy.receivers.client.monitoring.services.MonitoringService;
import eu.telecomnancy.receivers.client.monitoring.services.counters.TeamCounterService;
import eu.telecomnancy.receivers.client.monitoring.services.counters.UserCounterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void givenTheCreateTeamCommand_WhenUpdatingTheCounter_ThenTheTeamCountShouldBeIncremented() { }

    @Test
    public void givenTheCreateUserCommand_WhenUpdatingTheCounter_ThenTheUserCountShouldBeIncremented() { }

    @Test
    public void givenTheDeleteTeamCommand_WhenUpdatingTheCounter_ThenTheTeamCountShouldBeDecremented() { }

    @Test
    public void givenTheDeleteUserCommand_WhenUpdatingTheCounter_ThenTheUserCountShouldBeDecremented() { }

}
