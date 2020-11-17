package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.domain.Team;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Command to create a new Team
 *
 * @see eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService
 */
@Getter @Setter @ToString
public class CreateTeamCommand {

    /**
     * Name of the team
     */
    @NotBlank
    @Size(min = Team.NAME_MIN_LENGTH, max = Team.NAME_MAX_LENGTH)
    private String name;

}
