package eu.telecomnancy.membershipmanagement.api.services.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.GetUserQuery;
import eu.telecomnancy.membershipmanagement.api.domain.User;

import java.util.List;
import java.util.Optional;

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
    Optional<User> getUser(GetUserQuery getUserQuery);

    /**
     * Retrieve all users of the application
     *
     * @return A list containing all of the users
     */
    List<User> getUsers();

}
