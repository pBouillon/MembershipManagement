package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.PatchUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.commands.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.UserRepository;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.UnknownUserException;
import eu.telecomnancy.membershipmanagement.api.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void givenAnyId_WhenPatchingAnExistingUser_ThenNoExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(true);

        Mockito.when(userRepository.getOne(anyLong()))
                .thenReturn(new User());

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(new User());

        UserService userService = new UserService(userRepository, mapper);

        long targetUserId = 0;
        PatchUserCommand command = new PatchUserCommand();

        // Act + Assert
        assertDoesNotThrow(()
                -> userService.patchUser(targetUserId, command));
    }

    @Test
    void givenAnyId_WhenPatchingANonExistingUser_ThenAnUnknownUserExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(false);

        UserService userService = new UserService(userRepository, mapper);

        long targetUserId = 0;
        PatchUserCommand command = new PatchUserCommand();

        // Act + Assert
        assertThrows(
                UnknownUserException.class,
                () -> userService.patchUser(targetUserId, command));
    }

    @Test
    void givenAnyId_WhenUpdatingAnExistingUser_ThenNoExceptionShouldBeThrown() {
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

        // Act + Assert
        assertDoesNotThrow(()
                -> userService.updateUser(targetUserId, command));
    }

    @Test
    void givenAnyId_WhenUpdatingANonExistingUser_ThenAnUnknownUserExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(false);

        UserService userService = new UserService(userRepository, mapper);

        long targetUserId = 0;
        UpdateUserCommand command = new UpdateUserCommand();
        command.setId(targetUserId);

        // Act + Assert
        assertThrows(
                UnknownUserException.class,
                () -> userService.updateUser(targetUserId, command));
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
