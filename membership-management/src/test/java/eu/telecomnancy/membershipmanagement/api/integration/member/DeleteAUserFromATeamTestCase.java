package eu.telecomnancy.membershipmanagement.api.integration.member;

import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Case :
 *     (Read & Write operations)
 *     Test the deletion of a user from a team
 *
 * @see TeamWriteRestController
 * @see UserReadRestController
 * @see UserWriteRestController
 */
class DeleteAUserFromATeamTestCase extends IntegrationTest {

    /**
     * Ensure that when deleting a user from a team, the user is successfully withdrawn from it
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void deleteAUserFromATeam() throws URISyntaxException {
        // Create the team to be used
        CreateTeamCommand createTeamCommand = new CreateTeamCommand("ApprenTeam");

        URI teamCreationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdTeamResponse
                = restTemplate.postForEntity(teamCreationUri, createTeamCommand, TeamDto.class);

        assertEquals(createdTeamResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto createdTeam = extractPayload(createdTeamResponse);

        // Add a user
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

        // Remove the user from the team

        URI removeMembershipUri = getUrlForRoute("/api/teams/" + createdTeam.getId() + "/members/" + member.getId());

        restTemplate.delete(removeMembershipUri);

        // Ensure that the user does exist and does not belong to a team anymore
        ResponseEntity<UserDetailsDto> formerMemberResponse
                = restTemplate.getForEntity(retrieveUserUri, UserDetailsDto.class);

        UserDetailsDto formerMember = extractPayload(formerMemberResponse);
        assertNull(formerMember.getTeam());

        // Ensure that the team does exist and is empty
        URI retrievedTeamUri = getUrlForRoute("/api/teams/" + createdTeam.getId());

        ResponseEntity<TeamDetailsDto> formerTeamResponse
                = restTemplate.getForEntity(retrievedTeamUri, TeamDetailsDto.class);

        TeamDetailsDto formerTeam = extractPayload(formerTeamResponse);
        assertTrue(formerTeam.getMembers().isEmpty());
    }

}
