package eu.telecomnancy.membershipmanagement.api.services.exceptions;

/**
 * Custom exception occurring when attempting to retrieve a non-existing user
 */
public class UserNotFoundException extends MembershipManagementException {

    /**
     * Create the exception from the ids
     *
     * @param userId Invalid user id provided
     */
    public UserNotFoundException(long userId) {
        super(String.format(
                "No user found with id %d", userId));
    }

}
