package eu.telecomnancy.membershipmanagement.api.services.exceptions.user;

import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.MembershipManagementException;

/**
 * Custom exception occurring when attempting to add a user to a team when he already has one
 */
public class UserAlreadyInATeamException extends MembershipManagementException {

    /**
     * Create the exception from the id
     *
     * @param user User that cannot join the team
     * @param teamNotJoined The team he failed to join
     */
    public UserAlreadyInATeamException(User user, Team teamNotJoined) {
        super(String.format(
                "The user %s already belong to the team %s and cannot join the team %s",
                user,
                user.getTeam(),
                teamNotJoined));
    }

}
