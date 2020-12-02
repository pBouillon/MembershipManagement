package eu.telecomnancy.receivers.client.monitoring.services;

import eu.telecomnancy.receivers.client.monitoring.services.counters.Counter;
import eu.telecomnancy.receivers.client.monitoring.services.counters.TeamCounterService;
import eu.telecomnancy.receivers.client.monitoring.services.counters.UserCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * By default Service annotation creates a singleton scoped bean
 * Therefore, we do not need to implement the singleton pattern in addition to the service's definition
 */
@Service
public class MonitoringService {

    private final Counter teamCounterService;

    private final Counter userCounterService;

    private final Map<String, Runnable> actionMap = new HashMap<>();

    @Autowired
    public MonitoringService(TeamCounterService teamCounterService, UserCounterService userCounterService) {
        this.teamCounterService = teamCounterService;
        this.userCounterService = userCounterService;

        // Team count operations
        actionMap.put(TeamCounterService.DECREMENT_COUNT_OPERATION_NAME, teamCounterService::decrement);
        actionMap.put(TeamCounterService.INCREMENT_COUNT_OPERATION_NAME, teamCounterService::increment);

        // User count operation
        actionMap.put(UserCounterService.DECREMENT_COUNT_OPERATION_NAME, userCounterService::decrement);
        actionMap.put(UserCounterService.INCREMENT_COUNT_OPERATION_NAME, userCounterService::increment);
    }

    public void alterCountFromOperation(String operationPayload) {
        actionMap.keySet()
                .stream()
                .filter(operationPayload::contains)
                .findFirst()
                .ifPresent(key -> actionMap.get(key).run());
    }

    @Override
    public String toString() {
        return "teams: " + teamCounterService.getCount() + "\t|\tusers: " + userCounterService.getCount();
    }

}
