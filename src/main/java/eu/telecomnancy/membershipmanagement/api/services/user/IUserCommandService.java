package eu.telecomnancy.membershipmanagement.api.services.user;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.commands.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.springframework.data.util.Pair;

/**
 * Command part of the UserService
 * Specify the write-only commands
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 *
 * The returned values are the new state of the object, to be passed on to the controller
 * in order to respect the REST principles
 *
 * @see UserService
 */
public interface IUserCommandService {

    /**
     * Create or replace a {@link User} by its id
     * If no user exists at the provided id, create it; otherwise, replace it
     *
     * @param userId Id of the targeted user
     * @param command Payload holding the data to replace the existing ones
     * @return A pair containing the new state of the user (created or replaced) as key
     * and true if he as been created; false otherwise
     */
    Pair<User, Boolean> createOrReplaceUser(long userId, UpdateUserCommand command);

    /**
     * Store a new {@link User} in the database from the provided command
     *
     * @param command Payload to create the user
     * @return The user newly created
     */
    User createUser(CreateUserCommand command);

    /**
     * Given his id, replace the details of an existing {@link User}
     *
     * @param userId Id of the targeted user
     * @param command Payload holding the data to replace the existing ones
     * @return The user with the updated values
     */
    User updateUser(long userId, UpdateUserCommand command);

}
