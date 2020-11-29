package eu.telecomnancy.membershipmanagement.api.services.exceptions.team;

import eu.telecomnancy.membershipmanagement.api.services.exceptions.MembershipManagementException;

/**
 * Custom exception occurring when attempting to add a new member to a team that can't have any more of them
 */
public class TeamAlreadyCompleteException extends MembershipManagementException {

    /**
     * Create the exception
     */
    public TeamAlreadyCompleteException() {
        super("The team is full and can't accept any more members");
    }

}
