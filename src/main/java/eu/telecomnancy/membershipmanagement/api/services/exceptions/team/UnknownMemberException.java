package eu.telecomnancy.membershipmanagement.api.services.exceptions.team;

import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.MembershipManagementException;

/**
 * Custom exception occurring when attempting to remove a user from a team he does not belong to
 */
public class UnknownMemberException extends MembershipManagementException {

    /**
     * Create the exception from the id and the team
     *
     * @param userId Id the user causing the issue
     * @param team Team in which he should have been removed
     */
    public UnknownMemberException(long userId, Team team) {
        super(String.format(
                "Unable to remove the user of id %d from the team %s because he does not belong to it", userId, team));
    }

}
