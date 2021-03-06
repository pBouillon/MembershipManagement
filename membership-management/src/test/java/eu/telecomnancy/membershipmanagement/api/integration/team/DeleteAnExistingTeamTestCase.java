package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Case :
 *     (Write-only operation)
 *     Test that the team does not exists anymore when deleted
 *
 * @see TeamWriteRestController
 */
public class DeleteAnExistingTeamTestCase extends IntegrationTest {

    /**
     * Ensure that the team does not exists anymore when deleted
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void deleteAnExistingTeam() throws URISyntaxException {
        // Create the team to be used
        CreateTeamCommand createTeamCommand = new CreateTeamCommand("ApprenTeam");

        URI teamCreationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdTeamResponse
                = restTemplate.postForEntity(teamCreationUri, createTeamCommand, TeamDto.class);

        assertEquals(createdTeamResponse.getStatusCode(), HttpStatus.CREATED);

        TeamDto createdTeam = extractPayload(createdTeamResponse);

        // Delete the team
        URI teamDeletionUri = getUrlForRoute("/api/teams/" + createdTeam.getId());

        restTemplate.delete(teamDeletionUri);

        // Ensure that the team does not exist anymore
        URI teamUri = getUrlForRoute("/api/teams/" + createdTeam.getId());

        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity(teamUri, TeamDetailsDto.class));
    }

}
