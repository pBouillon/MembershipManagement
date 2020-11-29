package eu.telecomnancy.membershipmanagement.api.services.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.GetUserQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.GetUsersQuery;
import eu.telecomnancy.membershipmanagement.api.domain.User;

import java.util.List;

/**
 * Query part of the UserService
 * Specify the read-only commands
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 *
 * @see UserService
 */
public interface IUserQueryService {

    /**
     * Retrieve an user by its id
     *
     * @param getUserQuery Payload from which performing the search to retrieve a user
     * @return An optional user if he exists, no content if not
     */
    User getUser(GetUserQuery getUserQuery);

    /**
     * Retrieve all users of the application
     *
     * Given the query, a filter might be applied to retrieve only the users that belong or not belong to a team
     *
     * @return A list containing all of the users
     */
    List<User> getUsers(GetUsersQuery getUsersQuery);

}
