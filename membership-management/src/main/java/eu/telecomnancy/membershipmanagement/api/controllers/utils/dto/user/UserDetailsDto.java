package eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import lombok.Getter;
import lombok.Setter;

/**
 * User Details Data Transfer Object to be served and received by the API
 */
@Getter @Setter
public class UserDetailsDto {

    /**
     * Id of the user
     */
    private Long id;

    /**
     * Age of the user
     */
    private int age;

    /**
     * Firstname of the user
     */
    private String firstname;

    /**
     * Name of the user
     */
    private String name;

    /**
     * User's team
     */
    private TeamDto team;

}
