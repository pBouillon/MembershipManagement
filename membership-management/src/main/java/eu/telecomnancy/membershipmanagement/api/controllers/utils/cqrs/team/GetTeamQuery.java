package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.Query;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamQueryService;
import lombok.*;

/**
 * Query to get a team by its id
 *
 * @see ITeamQueryService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamQuery implements Query {

    /**
     * Id of the team
     */
    private long id;

}
