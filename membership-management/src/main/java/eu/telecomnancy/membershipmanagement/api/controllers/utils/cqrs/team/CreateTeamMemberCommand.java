package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.Command;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService;
import lombok.*;

/**
 * Command to add a user to an existing team
 *
 * @see ITeamCommandService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamMemberCommand implements Command {

    /**
     * Id of the {@link User} to add to the {@link Team}
     */
    private long memberToAddId;

}
