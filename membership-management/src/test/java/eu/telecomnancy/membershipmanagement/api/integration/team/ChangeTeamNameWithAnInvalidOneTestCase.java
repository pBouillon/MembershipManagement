package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.PatchTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.integration.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Route :
 *     /api/teams/:id
 *
 * Case :
 *     (Write-only operation)
 *     Test the team update
 *
 * @see TeamWriteRestController
 */
public class ChangeTeamNameWithAnInvalidOneTestCase extends IntegrationTest {

    /**
     * Ensure that the team name is not updated when a blank name is provided
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void updateTheTeamWithABlankName() throws URISyntaxException {
        // Create the team
        CreateTeamCommand createTeamCommand = new CreateTeamCommand("ApprenTeam");

        URI creationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdResponse
                = restTemplate.postForEntity(creationUri, createTeamCommand, TeamDto.class);

        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto created = extractPayload(createdResponse);

        // Change its name with a blank one
        PatchTeamCommand patchTeamCommand = new PatchTeamCommand();
        patchTeamCommand.setName(TestUtils.BLANK_STRING);

        URI updateUrl = getUrlForRoute("/api/teams/" + created.getId());

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(updateUrl, patchTeamCommand));
    }

    /**
     * Ensure that the team name is not updated when an empty name is provided
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void updateTheTeamWithAnEmptyName() throws URISyntaxException {
        // Create the team
        CreateTeamCommand createTeamCommand = new CreateTeamCommand("ApprenTeam");

        URI creationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdResponse
                = restTemplate.postForEntity(creationUri, createTeamCommand, TeamDto.class);

        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto created = extractPayload(createdResponse);

        // Change its name with an empty one
        PatchTeamCommand patchTeamCommand = new PatchTeamCommand();
        patchTeamCommand.setName(TestUtils.EMPTY_STRING);

        URI updateUrl = getUrlForRoute("/api/teams/" + created.getId());

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(updateUrl, patchTeamCommand));
    }

    /**
     * Ensure that the team name is not updated when a too long name is provided
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void updateTheTeamWithANameTooLong() throws URISyntaxException {
        // Create the team
        CreateTeamCommand createTeamCommand = new CreateTeamCommand("ApprenTeam");

        URI creationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdResponse
                = restTemplate.postForEntity(creationUri, createTeamCommand, TeamDto.class);

        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto created = extractPayload(createdResponse);

        // Change its name with a too long one
        PatchTeamCommand patchTeamCommand = new PatchTeamCommand();
        patchTeamCommand.setName(TestUtils.LONG_STRING);

        URI updateUrl = getUrlForRoute("/api/teams/" + created.getId());

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(updateUrl, patchTeamCommand));
    }

}
