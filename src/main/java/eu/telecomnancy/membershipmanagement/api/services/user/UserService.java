package eu.telecomnancy.membershipmanagement.api.services.user;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.commands.PatchUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.commands.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.UserRepository;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.UnknownUserException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle user-related operations
 */
@Log4j2
@Service
public class UserService implements IUserCommandService, IUserQueryService {

    /**
     * UserDto mapper utility
     */
    protected final UserMapper mapper;

    /**
     * Repository to access the `User` entity in the database
     */
    private final UserRepository userRepository;

    /**
     * Create a new instance of the UserService
     *
     * @param userRepository Repository to access the `User` entity in the database
     */
    @Autowired
    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(CreateUserCommand createUserCommand) {
        User created = userRepository.save(
                mapper.toUser(createUserCommand));

        log.info("New user created {}", created);

        return created;
    }

    /**
     * Check whether or not a user exists at the given id
     *
     * @param userId If of the user to check
     * @throws UnknownUserException If there is no user for the provided id
     */
    private void ensureUserIsExisting(long userId)
            throws UnknownUserException {
        if (userRepository.existsById(userId)) {
            return;
        }

        // If the user does not exists, throw an exception
        log.error("Unknown user of id {}", userId);
        throw new UnknownUserException(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        log.info("Retrieved {} users", users.size());

        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User patchUser(long userId, PatchUserCommand command)
            throws UnknownUserException {
        // If the user does not exists, throw an exception
        ensureUserIsExisting(userId);

        // Retrieve the user to update
        User target = userRepository.getOne(userId);

        // Perform the update
        log.info("Patch the user {} with {}", target, command);

        mapper.updateFromUser(
                mapper.toUser(command), target);

        log.info("Patched user: {}", target);

        // Return the saved instance
        return userRepository.save(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateUser(long userId, UpdateUserCommand command)
            throws UnknownUserException {
        // If the user does not exists, throw an exception
        ensureUserIsExisting(userId);

        // Retrieve the user to update
        User target = userRepository.getOne(userId);

        // Perform the update
        log.info("Update the user {} to {}", target, command);

        mapper.updateFromCommand(command, target);

        log.info("Updated user: {}", target);

        // Return the saved instance
        return userRepository.save(target);
    }

}
