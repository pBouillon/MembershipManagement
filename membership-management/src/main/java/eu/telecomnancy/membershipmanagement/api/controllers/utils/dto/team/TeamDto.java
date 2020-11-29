package eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team;

import lombok.Data;

/**
 * User Data Transfer Object to be served and received by the API
 */
@Data
public class TeamDto {

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

}
