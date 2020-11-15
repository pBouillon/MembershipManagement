package eu.telecomnancy.membershipmanagement.api.services.exceptions;

/**
 * Custom exception occurring when attempting to access a user that is not known by the system
 */
public class UnknownUserException extends MembershipManagementException {

    /**
     * Create the exception from the id
     *
     * @param userId Id the non-existing user
     */
    public UnknownUserException(long userId) {
        super(String.format(
                "Unknown user of id %d", userId));
    }

}
