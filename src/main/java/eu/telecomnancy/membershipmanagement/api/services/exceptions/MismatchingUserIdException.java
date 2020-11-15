package eu.telecomnancy.membershipmanagement.api.services.exceptions;

/**
 * Custom exception occurring when attempting to modify a user from mismatching ids
 */
public class MismatchingUserIdException extends MembershipManagementException {

    /**
     * Create the exception from the ids
     *
     * @param expected Original id
     * @param provided Mismatching id received
     */
    public MismatchingUserIdException(long expected, long provided) {
        super(String.format(
                "Mismatching id %d and %d",
                expected,
                provided));
    }

}
