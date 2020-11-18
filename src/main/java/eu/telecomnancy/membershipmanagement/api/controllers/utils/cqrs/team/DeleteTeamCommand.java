package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Command to delete a team
 *
 * @see ITeamCommandService
 */
@Getter @Setter @AllArgsConstructor
public class DeleteTeamCommand {

    /**
     * Id of the team to delete
     */
    private final long teamId;

}
