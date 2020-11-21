package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user;

import eu.telecomnancy.membershipmanagement.api.services.user.IUserQueryService;
import lombok.*;

/**
 * Query to get an user by its id
 *
 * @see IUserQueryService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserQuery {

    /**
     * Id of the user
     */
    private long id;

}
