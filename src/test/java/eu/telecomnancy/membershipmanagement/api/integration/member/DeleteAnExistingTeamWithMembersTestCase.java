package eu.telecomnancy.membershipmanagement.api.integration.member;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Routes :
 *     /api/teams/
 *     /api/teams/:id
 *     /api/users/
 *     /api/users/:id
 *
 * Case :
 *     (Read & Write operations)
 *     Test that when deleting a team which has a user, its user is not deleted too and does not have a team anymore
 *
 * @see TeamWriteRestController
 * @see UserReadRestController
 * @see UserWriteRestController
 */
class DeleteAnExistingTeamWithMembersTestCase extends IntegrationTest {

    /**
     * Ensure that when deleting a team which has a user, its user is not deleted too and does not have a team anymore
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void deleteATeamWithUsers() throws URISyntaxException {
        // Create the team to be used
        CreateTeamCommand createTeamCommand = new CreateTeamCommand("ApprenTeam");

        URI teamCreationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdTeamResponse
                = restTemplate.postForEntity(teamCreationUri, createTeamCommand, TeamDto.class);

        assertEquals(createdTeamResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto createdTeam = extractPayload(createdTeamResponse);

        // Add a user to the team
        CreateUserCommand createUserCommand = new CreateUserCommand(22, "Victor", "Varnier");

        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, createUserCommand, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);
        UserDto createdUser = extractPayload(createdUserResponse);

        // Add this user to the team
        CreateTeamMemberCommand createTeamMemberCommand = new CreateTeamMemberCommand();
        createTeamMemberCommand.setMemberToAddId(createdUser.getId());

        URI addMembershipUri = getUrlForRoute("/api/teams/" + createdTeam.getId() + "/members");

        // The return type does not matter here, we do not deserialize it
        ResponseEntity<Object> addedMembersListResponse
                = restTemplate.postForEntity(addMembershipUri, createTeamMemberCommand, Object.class);

        // Ensure that the user has the right team
        URI retrieveUserUri = getUrlForRoute("/api/users/" + createdUser.getId());

        ResponseEntity<UserDetailsDto> memberResponse
                = restTemplate.getForEntity(retrieveUserUri, UserDetailsDto.class);

        UserDetailsDto member = extractPayload(memberResponse);

        assertEquals(member.getTeam().getId(), createdTeam.getId());

        // Delete the team
        URI teamDeletionUri = getUrlForRoute("/api/teams/" + createdTeam.getId());

        restTemplate.delete(teamDeletionUri);

        // Ensure that the user does exist and does not belong to a team anymore
        ResponseEntity<UserDetailsDto> formerMemberResponse
                = restTemplate.getForEntity(retrieveUserUri, UserDetailsDto.class);

        UserDetailsDto formerMember = extractPayload(formerMemberResponse);
        assertNull(formerMember.getTeam());
    }

}
