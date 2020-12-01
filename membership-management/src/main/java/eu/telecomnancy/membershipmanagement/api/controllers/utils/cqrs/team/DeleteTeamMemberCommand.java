package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.Command;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService;
import lombok.*;

/**
 * Command to remove a user from an existing team
 *
 * @see ITeamCommandService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTeamMemberCommand implements Command {

    /**
     * Id of the user to be removed from the team
     */
    private long memberId;

    /**
     * Id of the team in which the member will be removed
     */
    private long teamId;

}
