package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Command to remove a user from an existing team
 *
 * @see eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService
 */
@Getter @Setter @AllArgsConstructor
public class DeleteTeamMemberCommand {

    /**
     * Id of the user to be removed from the team
     */
    private final long memberId;

    /**
     * Id of the team in which the member will be removed
     */
    private final long teamId;

}
