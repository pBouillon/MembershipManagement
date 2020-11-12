package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.domain.User;

import java.util.Arrays;
import java.util.List;

/**
 * Service to handle user-related operations
 */
public class UserService {

    /**
     * Retrieve all users of the application
     *
     * @return A list containing all of the users
     */
    public List<User> getUsers() {
        return Arrays.asList(
                new User(23, "Pierre", "Bouillon"),
                new User(22, "Victor", "Varnier")
        );
    }

}
