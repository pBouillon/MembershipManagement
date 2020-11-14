package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.UserRepository;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test suite for the UserService
 *
 * @see UserService
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /**
     * Mocked User repository to be injected for the unit tests
     */
    @Mock
    UserRepository userRepository;

    /**
     * Autowired mapper for unit testing purpose
     */
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    UserMapper mapper;

    @Test
    public void givenAnEmptyDatabase_WhenQueryingAllUsers_ThenNoneShouldBeRetrieved() {
        // Arrange
        Mockito.when(userRepository.findAll())
                .thenReturn(new ArrayList<>());

        UserService userService = new UserService(userRepository, mapper);

        // Act
        List<User> users = userService.getUsers();

        // Assert
        assertTrue(users.isEmpty());
    }

    @Test
    public void givenAPopulatedDatabase_WhenQueryingAllUsers_ThenAllShouldBeRetrieved() {
        // Arrange
        List<User> storedUsers = Arrays.asList(
                new User(23, "Pierre", "Bouillon"),
                new User(23, "Victor", "Varnier")
        );

        Mockito.when(userRepository.findAll())
                .thenReturn(storedUsers);

        UserService userService = new UserService(userRepository, mapper);

        // Act
        List<User> users = userService.getUsers();

        // Assert
        assertEquals(users, storedUsers);
    }

}
