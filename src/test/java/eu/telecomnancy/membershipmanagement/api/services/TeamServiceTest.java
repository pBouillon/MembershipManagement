package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.DeleteTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.DeleteTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.TeamRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.TeamAlreadyCompleteException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.UserNotAMemberOfTheTeamException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.UnknownTeamException;
import eu.telecomnancy.membershipmanagement.api.services.team.TeamService;
import eu.telecomnancy.membershipmanagement.api.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * Unit test suite for the TeamService
 *
 * @see TeamService
 */
@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    /**
     * Mocked Team repository to be injected for the unit tests
     */
    @Mock
    TeamRepository teamRepository;

    /**
     * Mocked user service to be injected in unit tests
     */
    @Mock
    UserService userService;

    /**
     * Team mapper
     */
    TeamMapper mapper = Mappers.getMapper(TeamMapper.class);

    @Test
    public void givenACompleteTeam_WhenAttemptingToAddAMember_ThenAnExceptionShouldBeThrown() {
        // Arrange
        // Create a team that will look like it is complete
        Team completeTeam = Mockito.mock(Team.class);
        Mockito.when(completeTeam.isTeamComplete())
                .thenReturn(true);

        Mockito.when(teamRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(completeTeam));

        // Create the ids (will not affect the test outcome)
        long teamId = 0;

        CreateTeamMemberCommand command = new CreateTeamMemberCommand();
        command.setMemberToAddId(0);

        // Create the service
        TeamService teamService = new TeamService(teamRepository, userService, mapper);

        // Act + Assert
        assertThrows(
                TeamAlreadyCompleteException.class,
                () -> teamService.addTeamMember(teamId, command));
    }

    @Test
    public void givenANonExistingTeam_WhenAttemptingToDeleteATeam_ThenAnExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(teamRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Create the id (will not affect the test outcome)
        long teamId = 1;

        DeleteTeamCommand command = new DeleteTeamCommand(teamId);

        // Create the service
        TeamService teamService = new TeamService(teamRepository, userService, mapper);

        // Act + Assert
        assertThrows(
                UnknownTeamException.class,
                () -> teamService.deleteTeam(command));
    }

    @Test
    public void givenAnUnknownTeam_WhenAttemptingToAddAMember_ThenAnExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(teamRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Create the ids (will not affect the test outcome)
        long teamId = 0;

        CreateTeamMemberCommand command = new CreateTeamMemberCommand();
        command.setMemberToAddId(0);

        // Create the service
        TeamService teamService = new TeamService(teamRepository, userService, mapper);

        // Act + Assert
        assertThrows(
                UnknownTeamException.class,
                () -> teamService.addTeamMember(teamId, command));
    }

    @Test
    public void givenANonExistingTeam_WhenRemovingAUser_ThenAnExceptionShouldBeThrown() {
        // Arrange
        Mockito.when(teamRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        final long userId = 0;
        final long teamId = 0;
        DeleteTeamMemberCommand deleteTeamMemberCommand = new DeleteTeamMemberCommand(userId, teamId);

        TeamService teamService = new TeamService(teamRepository, userService, mapper);

        // Act + Assert
        assertThrows(
                UnknownTeamException.class,
                () -> teamService.removeMemberFromTeam(deleteTeamMemberCommand));
    }

    @Test
    public void givenATeam_WhenRemovingAUserThatDoesNotBelongToIt_ThenAnExceptionShouldBeThrown() {
        // Arrange
        Team team = Mockito.mock(Team.class);
        Mockito.when(team.getMembers())
                .thenReturn(new ArrayList<>());

        Mockito.when(teamRepository.findById(anyLong()))
                .thenReturn(Optional.of(team));

        final long userId = 0;
        final long teamId = 0;
        DeleteTeamMemberCommand deleteTeamMemberCommand = new DeleteTeamMemberCommand(userId, teamId);

        TeamService teamService = new TeamService(teamRepository, userService, mapper);

        // Act + Assert
        assertThrows(
                UserNotAMemberOfTheTeamException.class,
                () -> teamService.removeMemberFromTeam(deleteTeamMemberCommand));
    }

}
