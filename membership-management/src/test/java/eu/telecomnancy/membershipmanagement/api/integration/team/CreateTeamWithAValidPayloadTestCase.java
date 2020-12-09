package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Case :
 *     (Write-only operation)
 *     Test the team creation
 *
 * @see TeamWriteRestController
 */
public class CreateTeamWithAValidPayloadTestCase extends IntegrationTest {

    /**
     * Ensure that the team creation is functional
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createTeamWithAValidName() throws URISyntaxException {
        // Prepare the payload
        CreateTeamCommand createTeamCommand = new CreateTeamCommand("ApprenTeam");

        // Perform the HTTP call
        URI creationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdResponse
                = restTemplate.postForEntity(creationUri, createTeamCommand, TeamDto.class);

        // Ensure that the creation successfully happened
        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto created = extractPayload(createdResponse);

        assertEquals(created.getName(), createTeamCommand.getName());
    }

}
