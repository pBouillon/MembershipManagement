package eu.telecomnancy.receivers.client.monitoring.services.counters;

import org.springframework.stereotype.Service;

/**
 * Counter for the user resources of the API
 */
@Service
public class UserCounterService extends CounterService {

    /**
     * Name of the API operation that should decrement the users count
     */
    public static final String DECREMENT_COUNT_OPERATION_NAME = "DeleteUserCommand";

    /**
     * Name of the API operation that should increment the users count
     */
    public static final String INCREMENT_COUNT_OPERATION_NAME = "CreateUserCommand";

}
