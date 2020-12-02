package eu.telecomnancy.receivers.client.monitoring.services.counters;

import org.springframework.stereotype.Service;

@Service
public class UserCounterService extends CounterService {

    public static final String DECREMENT_COUNT_OPERATION_NAME = "DeleteUserCommand";

    public static final String INCREMENT_COUNT_OPERATION_NAME = "CreateUserCommand";

}
