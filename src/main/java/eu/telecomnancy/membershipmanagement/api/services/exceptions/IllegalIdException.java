package eu.telecomnancy.membershipmanagement.api.services.exceptions;

/**
 * Custom exception occurring when attempting to retrieve an entity with
 * an invalid id
 */
public class IllegalIdException extends MembershipManagementException {

    /**
     * Create the exception from the id
     *
     * @param userId Invalid id provided
     */
    public IllegalIdException(long id) {
        super(String.format("Bad id: %d", id));
    }

}
