package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
 *     Test that the user filtering based on their hasTeam attribute is functional
 *
 * @see TeamWriteRestController
 * @see UserReadRestController
 * @see UserWriteRestController
 */
public class RetrieveUserOnTheirMembershipTestCase extends IntegrationTest {

    /**
     * Ensure that the user filtering based on their hasTeam attribute is functional
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void retrieveUserOnTheirMembership() throws URISyntaxException {
        // Create one team used in the test
        URI teamCreationUri = getUrlForRoute("/api/teams");

        // Team creation
        CreateTeamCommand createFirstTeamCommand = new CreateTeamCommand("Apprenteam");

        ResponseEntity<TeamDto> createdFirstTeamResponse
                = restTemplate.postForEntity(teamCreationUri, createFirstTeamCommand, TeamDto.class);

        assertEquals(createdFirstTeamResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto teamDto = extractPayload(createdFirstTeamResponse);

        // Create the first user
        URI userCreationUri = getUrlForRoute("/api/users");

        CreateUserCommand createUserCommand = new CreateUserCommand(35, "Mitth'raw", "Nuruodo");

        ResponseEntity<UserDto> createdResponse
                = restTemplate.postForEntity(userCreationUri, createUserCommand, UserDto.class);

        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        UserDto createdUser = extractPayload(createdResponse);

        // Get the id of the created user and add it to a list
        ArrayList<Long> createdUsersIds = new ArrayList<>();
        createdUsersIds.add(createdUser.getId());

        // Create a second user that will not belong to a team
        CreateUserCommand createUserNewCommand = new CreateUserCommand(42, "Mace", "Windu");

        // Perform the HTTP call
        URI creationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdNewResponse
                = restTemplate.postForEntity(creationUri, createUserNewCommand, UserDto.class);

        // Ensure that the creation successfully happened
        assertEquals(createdNewResponse.getStatusCode(), HttpStatus.CREATED);
        UserDto created = extractPayload(createdNewResponse);

        // Add the new created user's id to the list of created users id
        createdUsersIds.add(created.getId());

        // Add the first created user to the team
        CreateTeamMemberCommand createTeamMemberCommand = new CreateTeamMemberCommand();
        createTeamMemberCommand.setMemberToAddId(createdUser.getId());

        URI addMembershipUri = getUrlForRoute("/api/teams/" + teamDto.getId() + "/members");

        // The return type does not matter here, we do not deserialize it
        ResponseEntity<Object> addedMembersListResponse
                = restTemplate.postForEntity(addMembershipUri, createTeamMemberCommand, Object.class);

        // Ensure that team is the user's team
        URI retrieveTeamUri = getUrlForRoute("/api/teams/" + teamDto.getId() + "/members");

        ResponseEntity<List<UserDto>> membersResponse
                = restTemplate.exchange(retrieveTeamUri, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});

        assertEquals(membersResponse.getStatusCode(), HttpStatus.OK);
        List<UserDto> members = extractPayload(membersResponse);

        assertTrue(members.contains(createdUser));

        // Ensure that the result of the queries with and without filtering is coherent
        URI retrieveUsersUri = getUrlForRoute("/api/users");

        // Get non-filtered users
        ResponseEntity<List<UserDto>> unfilteredUsersResponse
                = restTemplate.exchange(retrieveUsersUri, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});

        List<UserDto> unfilteredUsers = extractPayload(unfilteredUsersResponse);

        // Both the users should be retrieved when no filtering has occurred
        ArrayList<Long> unfilteredUsersIds = new ArrayList<>();
        unfilteredUsers.forEach(userDto -> unfilteredUsersIds.add(userDto.getId()));

        assertTrue(unfilteredUsersIds.containsAll(createdUsersIds));

        // Get only users that belong to a team
        ResponseEntity<List<UserDto>> hasTeamUsersResponse
                = restTemplate.exchange(retrieveUsersUri + "?hasTeam=true",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        List<UserDto> hasTeamUsers = extractPayload(hasTeamUsersResponse);

        // The user that belongs to a team should be retrieved when querying the users that belong to a team
        assertTrue(hasTeamUsers.contains(createdUser));

        // Get only users that don't belong to a team
        ResponseEntity<List<UserDto>> hasNotTeamUsersResponse
                = restTemplate.exchange(retrieveUsersUri + "?hasTeam=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        List<UserDto> hasNotTeamUsers = extractPayload(hasNotTeamUsersResponse);

        // The user that doesn't belong to a team should be retrieved when querying the users that don't belong
        // to a team
        assertTrue(hasNotTeamUsers.contains(created));
    }

}
