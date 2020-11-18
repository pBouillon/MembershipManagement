package eu.telecomnancy.membershipmanagement.api.services.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.*;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.UserRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.user.UnknownUserException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.user.UserAlreadyInATeamException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to handle {@link User}-related operations
 */
@Log4j2
@Service
public class UserService implements IUserCommandService, IUserQueryService {

    /**
     * UserDto mapper utility
     */
    protected final UserMapper mapper;

    /**
     * Repository to access the {@link User} entity in the database
     */
    private final UserRepository userRepository;

    /**
     * Create a new instance of the UserService
     *
     * @param userRepository Repository to access the {@link User} entity in the database
     */
    @Autowired
    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    /**
     * Add a user to a team
     *
     * @param userId Id of the user to add to the team
     * @param team Team that will welcome the new user
     * @return The user newly added
     * @throws UnknownUserException If there is no user for the provided id
     */
    public User addToTeam(long userId, Team team)
            throws UnknownUserException {
        // Retrieve the user
        User user = retrieveUserById(userId);

        // Check if the user can join the team and doesn't not already has one
        if (user.isMemberOfATeam()) {
            log.error("The user {} already has a team and can't join the team {}", user, team);
            throw new UserAlreadyInATeamException(user, team);
        }

        // Perform the addition
        user.setTeam(team);
        return userRepository.save(user);
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
     * {@inheritDoc}
     */
    public void deleteUser(DeleteUserCommand deleteUserCommand)
            throws UnknownUserException {
        retrieveUserById(userId);

        userRepository.deleteById(userId);

        log.info("User of id {} successfully deleted", userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getUser(GetUserQuery getUserQuery) {
        long userId = getUserQuery.getId();
        Optional<User> optionalUser = userRepository.findById(userId);

        optionalUser.ifPresentOrElse(
                user -> log.info("Retrieved user {} from id {}", user, userId),
                () -> log.warn("Unable to retrieve a user with id {}", userId));

        return optionalUser;
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
        // Retrieve the user to update
        User target = retrieveUserById(userId);

        // Perform the update
        log.info("Patch the user {} with {}", target, command);

        mapper.updateFromUser(
                mapper.toUser(command), target);

        log.info("Patched user: {}", target);

        // Return the saved instance
        return userRepository.save(target);
    }
    /**
     * Try to retrieve a user by its id
     *
     * @param userId Id of the user to check
     * @throws UnknownUserException If there is no user for the provided id
     */
    public User retrieveUserById(long userId)
            throws UnknownUserException {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Unknown user of id {}", userId);
                    return new UnknownUserException(userId);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateUser(long userId, UpdateUserCommand command)
            throws UnknownUserException {
        // Retrieve the user to update
        User target = retrieveUserById(userId);

        // Perform the update
        log.info("Update the user {} to {}", target, command);

        mapper.updateFromCommand(command, target);

        log.info("Updated user: {}", target);

        // Return the saved instance
        return userRepository.save(target);
    }

}
