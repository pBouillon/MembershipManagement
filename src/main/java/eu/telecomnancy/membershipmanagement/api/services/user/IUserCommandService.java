package eu.telecomnancy.membershipmanagement.api.services.user;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.domain.User;

/**
 * Command part of the UserService
 * Specify the write-only commands
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 *
 * @see UserService
 */
public interface IUserCommandService {

    /**
     * Store a new {@link User} in the database from the provided command
     *
     * @param command Payload to create the user
     * @return The user newly created
     */
    User createUser(CreateUserCommand command);

}
