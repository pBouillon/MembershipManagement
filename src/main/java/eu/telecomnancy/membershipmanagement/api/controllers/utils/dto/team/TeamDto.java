package eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team;

import lombok.Getter;
import lombok.Setter;

/**
 * User Data Transfer Object to be served and received by the API
 */
@Getter @Setter
public class TeamDto {

    /**
     * Id of the user
     */
    private Long id;

    /**
     * Name of the user
     */
    private String name;

    /**
     * Whether the team is full or not
     */
    private boolean isComplete;
}
