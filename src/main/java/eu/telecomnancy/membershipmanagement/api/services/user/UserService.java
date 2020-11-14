package eu.telecomnancy.membershipmanagement.api.services.user;

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
     * Repository to access the `User` entity in the database
     */
    private final UserRepository userRepository;

    /**
     * Create a new instance of the UserService
     *
     * @param userRepository Repository to access the `User` entity in the database
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        log.info("UserService.getUsers : retrieved {} users", users.size());

        return users;
    }

}
