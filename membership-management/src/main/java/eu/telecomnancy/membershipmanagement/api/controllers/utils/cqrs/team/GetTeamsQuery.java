package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team;

import eu.telecomnancy.membershipmanagement.api.services.team.ITeamQueryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * Query to get all teams and optionally filter them to retrieve only the complete or not complete ones
 *
 * @see ITeamQueryService
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamsQuery {

    /**
     * Additional parameter to query only completed teams, not completed teams or not to perform any
     * filtering if the parameter is not provided
     */
    private Optional<Boolean> isComplete;

}
