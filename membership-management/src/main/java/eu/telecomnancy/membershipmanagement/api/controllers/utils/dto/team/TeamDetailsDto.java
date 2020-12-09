package eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team;

import java.time.Instant;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import lombok.Data;

import java.util.List;

/**
 * User Data Transfer Object to be served and received by the API
 */
@Data
public class TeamDetailsDto {

    /**
     * Creation date of the team, represented as UTC
     */
    private Instant creationDate;

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
