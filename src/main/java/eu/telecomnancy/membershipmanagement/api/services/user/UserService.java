package eu.telecomnancy.membershipmanagement.api.services.user;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.commands.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.UserRepository;
import eu.telecomnancy.membershipmanagement.api.domain.User;
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
    public User createOrReplaceUser(long userId, UpdateUserCommand command) {
        // Ensure that the ids are matching
        if (userId != command.getId()) {
            IllegalArgumentException exception = new IllegalArgumentException("Id does not match");

            log.error("Attempted to update the user with values {} with a mismatching id {}", command, userId);

            throw exception;
        }

        // Create the user if he does not exists, replace him otherwise
        return ! userRepository.existsById(userId)
                ? createUser(mapper.toCreateUserCommand(command))
                : updateUser(userId, command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(CreateUserCommand createUserCommand) {
        User created = userRepository.save(
                mapper.toUser(createUserCommand));

        log.info("Created new user {}", created);

        return created;
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
    public User updateUser(long userId, UpdateUserCommand command) {
        User target = userRepository.getOne(userId);

        log.info("Update the user {} to {}", target, command);
        mapper.updateFromCommand(command, target);

        return userRepository.save(target);
    }

}
