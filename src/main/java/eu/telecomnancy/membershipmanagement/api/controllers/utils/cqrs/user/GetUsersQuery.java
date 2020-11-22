package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user;

import eu.telecomnancy.membershipmanagement.api.services.user.IUserQueryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * Query to get all users and optionally filter them to retrieve only the users that belong to a team or the ones
 * who don't belong to a team
 *
 * @see IUserQueryService
 */

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUsersQuery {

    /**
     * Additional parameter to query only users who belong to a team, who don't belong to a team or not to perform any
     * filtering if the parameter is not provided
     */
    private Optional<Boolean> hasTeam;

}
