package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Route :
 *     /api/teams
 *
 * Case :
 *     (Write-only operation)
 *     Test the team creation
 *
 * @see TeamWriteRestController
 */
public class CreateTeamWithAValidPayloadTestCase extends IntegrationTest {

    /**
     * Ensure that the user creation is functional
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createTeamWithAValidName() throws URISyntaxException {
        // Prepare the payload
        Team toCreate = new Team("ApprenTeam");

        // Perform the HTTP call
        URI creationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdResponse
                = restTemplate.postForEntity(creationUri, toCreate, TeamDto.class);

        // Ensure that the creation successfully happened
        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);

        TeamDto created = createdResponse.getBody();
        assertNotNull(created);

        assertEquals(created.getName(), toCreate.getName());
    }

}
