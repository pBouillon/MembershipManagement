package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Command to add a user to an existing team
 *
 * @see eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService
 */
@Getter @Setter @ToString
public class CreateTeamMemberCommand {

    /**
     * Id of the {@link User} to add to the {@link Team}
     */
    private long memberToAddId;

}
