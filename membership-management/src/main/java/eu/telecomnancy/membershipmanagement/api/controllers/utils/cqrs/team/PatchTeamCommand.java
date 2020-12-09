package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.Command;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Command to rename a team
 *
 * @see ITeamCommandService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchTeamCommand implements Command {

    /**
     * Name of the team
     */
    @NotBlank
    @Size(min = Team.NAME_MIN_LENGTH, max = Team.NAME_MAX_LENGTH)
    private String name;

}
