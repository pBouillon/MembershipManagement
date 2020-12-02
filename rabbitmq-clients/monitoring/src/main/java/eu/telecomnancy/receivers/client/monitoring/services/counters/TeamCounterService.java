package eu.telecomnancy.receivers.client.monitoring.services.counters;

import org.springframework.stereotype.Service;

/**
 * Counter for the team resources of the API
 */
@Service
public class TeamCounterService extends CounterService {

    /**
     * Name of the API operation that should decrement the teams count
     */
    public static final String DECREMENT_COUNT_OPERATION_NAME = "DeleteTeamCommand";

    /**
     * Name of the API operation that should increment the teams count
     */
    public static final String INCREMENT_COUNT_OPERATION_NAME = "CreateTeamCommand";

}
