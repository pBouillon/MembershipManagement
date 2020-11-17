package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.services.team.ITeamQueryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Query to get an team by its id
 *
 * @see ITeamQueryService
 */
@Getter @Setter @AllArgsConstructor
public class GetTeamQuery {

    /**
     * Id of the team
     */
    private long id;

}
