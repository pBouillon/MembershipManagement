package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.DeleteUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.GetUsersQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.PatchUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.UserRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.user.UnknownUserException;
import eu.telecomnancy.membershipmanagement.api.services.notification.MessagingService;
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
import java.util.Optional;

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
     * Mocked messaging service to be injected for the unit tests
     */
    @Mock
    MessagingService messagingService;

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

        UserService userService = new UserService(messagingService, userRepository, mapper);

        GetUsersQuery getUsersQuery = new GetUsersQuery(Optional.empty());

        // Act
        List<User> users = userService.getUsers(getUsersQuery);

        // Assert
        assertTrue(users.isEmpty());
    }

    @Test
    void givenAnyId_WhenDeletingANonExistingUser_ThenAnUnknownUserExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        long targetUserId = 0;
        UserService userService = new UserService(messagingService, userRepository, mapper);
        DeleteUserCommand command = new DeleteUserCommand(targetUserId);

        // Act + Assert
        assertThrows(
                UnknownUserException.class,
                () -> userService.deleteUser(command));
    }

    @Test
    void givenAnyId_WhenPatchingAnExistingUser_ThenNoExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(new User()));

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(new User());

        UserService userService = new UserService(messagingService, userRepository, mapper);

        long targetUserId = 0;
        PatchUserCommand command = new PatchUserCommand();

        // Act + Assert
        assertDoesNotThrow(()
                -> userService.patchUser(targetUserId, command));
    }

    @Test
    void givenAnyId_WhenPatchingANonExistingUser_ThenAnUnknownUserExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        UserService userService = new UserService(messagingService, userRepository, mapper);

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
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(new User()));

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(new User());

        UserService userService = new UserService(messagingService, userRepository, mapper);

        long targetUserId = 0;
        UpdateUserCommand command = new UpdateUserCommand();

        // Act + Assert
        assertDoesNotThrow(()
                -> userService.updateUser(targetUserId, command));
    }

    @Test
    void givenAnyId_WhenUpdatingANonExistingUser_ThenAnUnknownUserExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        UserService userService = new UserService(messagingService, userRepository, mapper);

        long targetUserId = 0;
        UpdateUserCommand command = new UpdateUserCommand();

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

        UserService userService = new UserService(messagingService, userRepository, mapper);

        GetUsersQuery getUsersQuery = new GetUsersQuery(Optional.empty());

        // Act
        List<User> users = userService.getUsers(getUsersQuery);

        // Assert
        assertEquals(users, storedUsers);
    }

    @Test
    public void givenUsersWithOrWithoutTeam_WhenQueryingAllOfThem_BothShouldBeRetrieved() {
        // Arrange
        User userWithTeam = new User(100, "Sheev", "Palpatine");
        userWithTeam.setTeam(new Team("Apprenteam"));

        User userWithoutTeam = new User(25,"Luke","Skywalker");

        List<User> users = Arrays.asList(userWithTeam, userWithoutTeam);

        Mockito.when(userRepository.findAll())
                .thenReturn(users);

        GetUsersQuery getUsersQuery = new GetUsersQuery(Optional.empty());

        UserService userService = new UserService(messagingService, userRepository, mapper);

        // Act
        List<User> retrievedUsers = userService.getUsers(getUsersQuery);

        // Assert
        assertEquals(retrievedUsers.size(), users.size());
    }

    @Test
    public void givenUsersWithOrWithoutTeam_WhenQueryingTheOnesWithoutTeam_OnlyTheWithoutTeamUsersShouldBeRetrieved() {
        // Arrange
        User userWithTeam = new User(100, "Sheev", "Palpatine");
        userWithTeam.setTeam(new Team("Apprenteam"));

        Mockito.when(userRepository.findByTeamNotNull())
                .thenReturn(List.of(userWithTeam));

        GetUsersQuery getUsersQuery = new GetUsersQuery(Optional.of(true));

        UserService userService = new UserService(messagingService, userRepository, mapper);

        // Act
        List<User> retrievedUsers = userService.getUsers(getUsersQuery);

        // Assert
        assertEquals(retrievedUsers, List.of(userWithTeam));
    }

    @Test
    public void givenUsersWithOrWithoutTeam_WhenQueryingTheOnesWithTeam_OnlyTheWithoutTeamUsersShouldBeRetrieved() {
        // Arrange
        User userWithoutTeam = new User(23, "Cal", "Kestis");

        Mockito.when(userRepository.findByTeamNull())
                .thenReturn(List.of(userWithoutTeam));

        GetUsersQuery getUsersQuery = new GetUsersQuery(Optional.of(false));

        UserService userService = new UserService(messagingService, userRepository, mapper);

        // Act
        List<User> retrievedUsers = userService.getUsers(getUsersQuery);

        // Assert
        assertEquals(retrievedUsers, List.of(userWithoutTeam));
    }

}
