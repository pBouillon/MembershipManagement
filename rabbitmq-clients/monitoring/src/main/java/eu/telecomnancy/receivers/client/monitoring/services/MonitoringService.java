package eu.telecomnancy.receivers.client.monitoring.services;

import eu.telecomnancy.receivers.client.monitoring.services.counters.Counter;
import eu.telecomnancy.receivers.client.monitoring.services.counters.TeamCounterService;
import eu.telecomnancy.receivers.client.monitoring.services.counters.UserCounterService;
import eu.telecomnancy.receivers.client.monitoring.utils.ColorUtils;
import eu.telecomnancy.receivers.client.monitoring.utils.Colors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Orchestrator to manage the count of the various resources of the API
 *
 * By default Service annotation creates a singleton scoped bean
 * Therefore, we do not need to implement the singleton pattern in addition to the service's definition
 */
@Service
public class MonitoringService {

    /**
     * Inner-counter for the team resources
     */
    private final Counter teamCounterService;

    /**
     * Inner-counter for the user resources
     */
    private final Counter userCounterService;

    /**
     * Map the actions to perform on the counter based on the operation name
     */
    private final Map<String, Runnable> actionMap = new HashMap<>();

    /**
     * Create the service
     *
     * @param teamCounterService Inner-counter for the team resources
     * @param userCounterService Inner-counter for the user resources
     */
    @Autowired
    public MonitoringService(TeamCounterService teamCounterService, UserCounterService userCounterService) {
        this.teamCounterService = teamCounterService;
        this.userCounterService = userCounterService;

        // Team count operations
        actionMap.put(TeamCounterService.DECREMENT_COUNT_OPERATION_NAME, teamCounterService::decrement);
        actionMap.put(TeamCounterService.INCREMENT_COUNT_OPERATION_NAME, teamCounterService::increment);

        // User count operations
        actionMap.put(UserCounterService.DECREMENT_COUNT_OPERATION_NAME, userCounterService::decrement);
        actionMap.put(UserCounterService.INCREMENT_COUNT_OPERATION_NAME, userCounterService::increment);
    }

    /**
     * Alter the count held by the dedicated counter
     *
     * @param operationPayload Name of the operation performed by the API
     */
    public void alterCountFromOperation(String operationPayload) {
        actionMap.keySet()
                .stream()
                .filter(operationPayload::contains)
                .findFirst()
                .ifPresent(key -> actionMap.get(key).run());
    }

    @Override
    public String toString() {
        return "teams: "
                + ColorUtils.getColoredString(
                        String.valueOf(teamCounterService.getCount()), Colors.CYAN)
                + "\t|\tusers: "
                + ColorUtils.getColoredString(
                        String.valueOf(userCounterService.getCount()), Colors.CYAN);
    }

}
