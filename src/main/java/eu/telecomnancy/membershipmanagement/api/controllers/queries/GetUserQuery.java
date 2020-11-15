package eu.telecomnancy.membershipmanagement.api.controllers.queries;

import eu.telecomnancy.membershipmanagement.api.services.user.IUserQueryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Query to get an user by its id
 *
 * @see IUserQueryService
 */
@Getter @Setter @AllArgsConstructor
public class GetUserQuery {

    /**
     * Id of the user
     */
    private long id;

}
