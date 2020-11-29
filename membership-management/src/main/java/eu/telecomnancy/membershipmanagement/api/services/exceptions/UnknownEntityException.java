package eu.telecomnancy.membershipmanagement.api.services.exceptions;

/**
 * Custom exception thrown when an unknown entity is trying to be fetched
 */
public abstract class UnknownEntityException extends MembershipManagementException {

    /**
     * Create the exception
     *
     * @param message Reason of the exception
     */
    public UnknownEntityException(String message) {
        super(message);
    }

}
