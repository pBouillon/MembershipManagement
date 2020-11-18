package eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * User Data Transfer Object to be served and received by the API
 */
@Getter @Setter
public class TeamDetailsDto {

    /**
     * Id of the team
     */
    private Long id;

    /**
     * Name of the team
     */
    private String name;

    /**
     * Whether the team is full or not
     */
    private boolean isComplete;

    /**
     * Members of the team
     */
    private List<UserDto> members;

}

