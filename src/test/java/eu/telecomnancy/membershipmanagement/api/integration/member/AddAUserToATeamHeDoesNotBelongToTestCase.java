package eu.telecomnancy.membershipmanagement.api.integration.member;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
 *     Test that a team can successfully have a new member
 *
 * @see TeamWriteRestController
 * @see UserReadRestController
 * @see UserWriteRestController
 */
public class AddAUserToATeamHeDoesNotBelongToTestCase extends IntegrationTest {

    /**
     * Ensure that a team can successfully have a new member
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void addAUserToATeam() throws URISyntaxException {
        // Create a new team
        Team teamToCreate = new Team("ApprenTeam");

        URI teamCreationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdTeamResponse
                = restTemplate.postForEntity(teamCreationUri, teamToCreate, TeamDto.class);

        assertEquals(createdTeamResponse.getStatusCode(), HttpStatus.CREATED);

        TeamDto createdTeam = createdTeamResponse.getBody();
        assertNotNull(createdTeam);

        // Create a new user
        User userToCreate = new User(22, "Victor", "Varnier");

        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = createdUserResponse.getBody();
        assertNotNull(createdUser);

        // Add the user to the team
        CreateTeamMemberCommand createTeamMemberCommand = new CreateTeamMemberCommand();
        createTeamMemberCommand.setMemberToAddId(createdUser.getId());

        URI addMembershipUri = getUrlForRoute("/api/teams/" + createdTeam.getId() + "/members");

        // The return type does not matter here, we do not deserialize it
        ResponseEntity<Object> addedMembersListResponse
                = restTemplate.postForEntity(addMembershipUri, createTeamMemberCommand, Object.class);

        // Ensure that team is the user's team
        URI retrieveTeamUri = getUrlForRoute("/api/teams/" + createdTeam.getId() + "/members");

        ResponseEntity<List<UserDto>> membersResponse
                = restTemplate.exchange(retrieveTeamUri, HttpMethod.GET, null, new ParameterizedTypeReference<>() { });

        assertEquals(membersResponse.getStatusCode(), HttpStatus.OK);

        List<UserDto> members = membersResponse.getBody();

        assertNotNull(members);
        assertTrue(members.contains(createdUser));

        // Ensure that the user is in the team members
        URI retrieveUserUri = getUrlForRoute("/api/users/" + createdUser.getId());

        ResponseEntity<UserDetailsDto> memberResponse
                = restTemplate.getForEntity(retrieveUserUri, UserDetailsDto.class);

        UserDetailsDto member = memberResponse.getBody();
        assertNotNull(member);

        assertEquals(member.getTeam(), createdTeam);
    }

}
