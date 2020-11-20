package eu.telecomnancy.membershipmanagement.api.integration.member;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Routes :
 *     /api/teams/
 *     /api/teams/:id/members
 *     /api/users/
 *     /api/users/:id
 *
 * Case :
 *     (Read & Write operations)
 *     Test that a user can't join a team twice
 *
 * @see TeamWriteRestController
 * @see UserReadRestController
 * @see UserWriteRestController
 */
public class AddAUserToATeamThatHeIsAlreadyAMemberOfTestCase extends IntegrationTest {

    /**
     * Ensure that a user can't join a team twice
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void addAUserToATeamHeAlreadyBelongsTo() throws URISyntaxException {
        // Create a new team
        Team teamToCreate = new Team("ApprenTeam");

        URI teamCreationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdTeamResponse
                = restTemplate.postForEntity(teamCreationUri, teamToCreate, TeamDto.class);

        assertEquals(createdTeamResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto createdTeam = extractPayload(createdTeamResponse);

        // Create a new user
        User userToCreate = new User(22, "Victor", "Varnier");

        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);
        UserDto createdUser = extractPayload(createdUserResponse);

        // Add the user to the team
        CreateTeamMemberCommand createTeamMemberCommand = new CreateTeamMemberCommand();
        createTeamMemberCommand.setMemberToAddId(createdUser.getId());

        URI addMembershipUri = getUrlForRoute("/api/teams/" + createdTeam.getId() + "/members");

        ResponseEntity<Object> addedMembersListResponse
                = restTemplate.postForEntity(addMembershipUri, createTeamMemberCommand, Object.class);

        // Ensure that team is the user's team
        URI retrieveTeamUri = getUrlForRoute("/api/teams/" + createdTeam.getId() + "/members");

        ResponseEntity<List<UserDto>> membersResponse
                = restTemplate.exchange(retrieveTeamUri, HttpMethod.GET, null, new ParameterizedTypeReference<>() { });

        assertEquals(membersResponse.getStatusCode(), HttpStatus.OK);

        List<UserDto> members = extractPayload(membersResponse);
        assertTrue(members.contains(createdUser));

        // Attempt to add this user once again
        assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(addMembershipUri, createTeamMemberCommand, Object.class));
    }

}
