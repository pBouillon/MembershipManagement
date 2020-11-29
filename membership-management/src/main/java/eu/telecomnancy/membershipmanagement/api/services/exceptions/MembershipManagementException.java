package eu.telecomnancy.membershipmanagement.api.services.exceptions;

import java.util.Collections;
import java.util.Map;

/**
 * Generic exception for the Membership Management API
 */
public abstract class MembershipManagementException extends RuntimeException {

    /**
     * Create the exception
     *
     * @param message Reason of the exception
     */
    public MembershipManagementException(String message) {
        super(message);
    }

    /**
     * Get the formatted exception as a pair formatted as:
     * "reason": message
     *
     * @return The exception pair
     */
    public Map<String, String> getReason() {
        return Collections.singletonMap("reason", getMessage());
    }

}
