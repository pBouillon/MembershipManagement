package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Route :
 *     /api/teams/:id
 *
 * Case :
 *     (Read & Write operation)
 *     Test that the controller successfully retrieve an existing team
 *
 * @see TeamReadRestController
 */
public class RetrieveATeamTestCase extends IntegrationTest {

    /**
     * Ensure that if a team has been created, we can retrieve it
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void retrieveACreatedUser() throws URISyntaxException {
        // Create a new team in the system
        CreateTeamCommand createTeamCommand = new CreateTeamCommand("ApprenTeam");

        URI creationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdResponse
                = restTemplate.postForEntity(creationUri, createTeamCommand, TeamDto.class);

        // Ensure that the team is created and retrieve its id
        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);

        TeamDto createdTeamDto = extractPayload(createdResponse);

        // Perform the HTTP call to retrieve the details of the created team based on its id
        URI retrieveUri = getUrlForRoute("/api/teams/" + createdTeamDto.getId());

        ResponseEntity<TeamDetailsDto> retrievedResponse
                = restTemplate.getForEntity(retrieveUri, TeamDetailsDto.class);

        // Ensure that the team retrieved is indeed the same as the one we created
        assertEquals(retrievedResponse.getStatusCode(), HttpStatus.OK);

        TeamDetailsDto retrieved = extractPayload(retrievedResponse);

        assertEquals(retrieved.getName(), createTeamCommand.getName());
        assertEquals(retrieved.getMembers(), Collections.emptyList());
    }

}
