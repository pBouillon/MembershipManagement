package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.UserRepository;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

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
     * User mapper
     */
    UserMapper mapper = Mappers.getMapper(UserMapper.class);

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

    @Test
    void givenMatchingId_WhenUpdatingAnExistingUser_ThenTheReturnedCreatedFlagShouldBeFalse() {
        // Arrange
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(true);

        Mockito.when(userRepository.getOne(anyLong()))
                .thenReturn(new User());

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(new User());

        UserService userService = new UserService(userRepository, mapper);

        long targetUserId = 0;
        UpdateUserCommand command = new UpdateUserCommand();
        command.setId(targetUserId);

        // Act
        Pair<User, Boolean> result = userService.createOrReplaceUser(targetUserId, command);

        // Assert
        boolean isCreated = result.getSecond();
        assertFalse(isCreated);
    }

    @Test
    void givenMatchingId_WhenUpdatingANonExistingUser_ThenTheReturnedCreatedFlagShouldBeTrue() {
        // Arrange
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(false);

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(new User());

        UserService userService = new UserService(userRepository, mapper);

        long targetUserId = 0;
        UpdateUserCommand command = new UpdateUserCommand();
        command.setId(targetUserId);

        // Act
        Pair<User, Boolean> result = userService.createOrReplaceUser(targetUserId, command);

        // Assert
        boolean isCreated = result.getSecond();
        assertTrue(isCreated);
    }

    @Test
    void givenMismatchingId_WhenAttemptingToUpdateTheUser_AnExceptionMustBeThrown() {
        // Arrange
        UserService userService = new UserService(userRepository, mapper);

        long targetUserId = 1;

        UpdateUserCommand command = new UpdateUserCommand();
        command.setId(targetUserId + 1);

        // Act + Assert
        assertThrows(
                RuntimeException.class,
                () -> userService.updateUser(targetUserId, command));
    }

}
