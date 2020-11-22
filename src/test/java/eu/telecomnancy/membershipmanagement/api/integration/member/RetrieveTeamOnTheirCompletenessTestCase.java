package eu.telecomnancy.membershipmanagement.api.integration.member;

import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Routes :
 *     /api/teams/
 *     /api/teams/:id/members
 *     /api/users/
 *
 * Case :
 *     (Read & Write operations)
 *     Test that the team filtering based on their isComplete attribute is functional
 *
 * @see TeamWriteRestController
 * @see UserReadRestController
 * @see UserWriteRestController
 */
public class RetrieveTeamOnTheirCompletenessTestCase extends IntegrationTest {

    /**
     * Ensure that the team filtering based on their isComplete attribute is functional
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void retrieveTeamOnTheirCompleteness() throws URISyntaxException {
        // Create two teams used in the test
        URI teamCreationUri = getUrlForRoute("/api/teams");

        // First team creation
        CreateTeamCommand createFirstTeamCommand = new CreateTeamCommand("first");

        ResponseEntity<TeamDto> createdFirstTeamResponse
                = restTemplate.postForEntity(teamCreationUri, createFirstTeamCommand, TeamDto.class);

        assertEquals(createdFirstTeamResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto first = extractPayload(createdFirstTeamResponse);

        // Second team creation
        CreateTeamCommand createSecondTeamCommand = new CreateTeamCommand("second");

        ResponseEntity<TeamDto> createdSecondTeamResponse
                = restTemplate.postForEntity(teamCreationUri, createSecondTeamCommand, TeamDto.class);

        assertEquals(createdSecondTeamResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto second = extractPayload(createdSecondTeamResponse);

        List<Long> createdTeamsIds = toIds(Arrays.asList(first, second));

        // Create enough user to fill a team
        URI userCreationUri = getUrlForRoute("/api/users");

        List<CreateUserCommand> createUserCommands = new ArrayList<>();

        for (int i = 0; i < Team.MAX_MEMBERS; ++i) {
            createUserCommands.add(new CreateUserCommand(42, "John", "Doe"));
        }

        List<UserDto> createdUsers = createUserCommands.stream()
                .map(createUserCommand
                        -> restTemplate.postForEntity(userCreationUri, createUserCommand, UserDto.class))
                .map(this::extractPayload)
                .collect(Collectors.toList());

        // Ensure that the result of the queries with and without filtering is coherent
        URI retrieveTeamsUri = getUrlForRoute("/api/teams");

        // Get non-filtered teams
        ResponseEntity<List<TeamDto>> unfilteredTeamsResponse
                = restTemplate.exchange(retrieveTeamsUri, HttpMethod.GET, null, new ParameterizedTypeReference<>() { });

        List<TeamDto> unfilteredTeams = extractPayload(unfilteredTeamsResponse);

        // Both the teams should be retrieved when no filtering has occurred
        List<Long> unfilteredTeamsIds = toIds(unfilteredTeams);
        assertTrue(unfilteredTeamsIds.containsAll(createdTeamsIds));

        // Get only complete teams
        ResponseEntity<List<TeamDto>> completeTeamsResponse
                = restTemplate.exchange(retrieveTeamsUri + "?isComplete=true",
                    HttpMethod.GET, null, new ParameterizedTypeReference<>() { });

        List<TeamDto> completeTeams = extractPayload(completeTeamsResponse);

        // Neither of the teams are complete on creation
        List<Long> completeTeamsIds = toIds(completeTeams);
        assertTrue(completeTeamsIds.stream()
                .noneMatch(createdTeamsIds::contains));

        // Get only incomplete teams
        ResponseEntity<List<TeamDto>> incompleteTeamsResponse
                = restTemplate.exchange(retrieveTeamsUri + "?isComplete=false",
                    HttpMethod.GET, null, new ParameterizedTypeReference<>() { });

        List<TeamDto> incompleteTeams = extractPayload(incompleteTeamsResponse);

        // Both team are incomplete after being created
        List<Long> incompleteTeamsIds = toIds(incompleteTeams);
        assertTrue(incompleteTeamsIds.containsAll(createdTeamsIds));

        // Fill the first team
        URI addMemberToFirstTeamUri = getUrlForRoute("/api/teams/" + first.getId() + "/members");

        createdUsers.stream()
                .map(createdUser
                        -> new CreateTeamMemberCommand(createdUser.getId()))
                .forEach(addTeamMemberCommand
                        -> restTemplate.postForEntity(addMemberToFirstTeamUri, addTeamMemberCommand, Object.class));

        // Ensure that the result of the queries with and without filtering is coherent
        // Get non-filtered teams
        unfilteredTeamsResponse
                = restTemplate.exchange(retrieveTeamsUri, HttpMethod.GET, null, new ParameterizedTypeReference<>() { });

        unfilteredTeams = extractPayload(unfilteredTeamsResponse);

        // Both the teams should be retrieved when no filtering has occurred
        unfilteredTeamsIds = toIds(unfilteredTeams);
        assertTrue(unfilteredTeamsIds.containsAll(createdTeamsIds));

        // Get only complete teams
        completeTeamsResponse
                = restTemplate.exchange(retrieveTeamsUri + "?isComplete=true",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() { });

        completeTeams = extractPayload(completeTeamsResponse);

        // The first team is now complete and should be retrieved when querying the completed teams
        assertTrue(completeTeams.stream()
                .map(TeamDto::getId)
                .anyMatch(retrievedTeamId -> retrievedTeamId.equals(first.getId())));

        // Get only incomplete teams
        incompleteTeamsResponse
                = restTemplate.exchange(retrieveTeamsUri + "?isComplete=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() { });

        incompleteTeams = extractPayload(incompleteTeamsResponse);

        // The first team is now complete and should not appear when filtering only the incomplete teams anymore
        assertTrue(incompleteTeams.stream()
                .map(TeamDto::getId)
                .noneMatch(retrievedTeamId -> retrievedTeamId.equals(first.getId())));
    }

    /**
     * Convert a list of team DTO to a list of their ids
     *
     * @param teamDtos DTOs to convert
     * @return The list of their ids
     */
    private List<Long> toIds(List<TeamDto> teamDtos) {
        return teamDtos.stream()
                .map(TeamDto::getId)
                .collect(Collectors.toList());
    }

}
