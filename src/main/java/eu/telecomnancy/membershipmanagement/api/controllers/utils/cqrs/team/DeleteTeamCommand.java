package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService;
import lombok.*;

/**
 * Command to delete a team
 *
 * @see ITeamCommandService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTeamCommand {

    /**
     * Id of the team to delete
     */
    private long teamId;

}
