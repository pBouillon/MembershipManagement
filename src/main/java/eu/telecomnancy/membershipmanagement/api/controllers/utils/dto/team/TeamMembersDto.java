package eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Team members Data Transfer Object to be served and received by the API
 */
@Getter @Setter
public class TeamMembersDto {

    /**
     * All the team members
     */
    private List<UserDto> members;

}
