package eu.telecomnancy.membershipmanagement.api.services.user;

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
     * Retrieve all users of the application
     *
     * @return A list containing all of the users
     */
    List<User> getUsers();

}
