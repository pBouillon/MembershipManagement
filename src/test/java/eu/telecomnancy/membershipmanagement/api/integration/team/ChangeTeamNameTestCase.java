package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.UpdateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

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
public class ChangeTeamNameTestCase extends IntegrationTest {

    /**
     * Ensure that the team's data are successfully updated
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void updateTheTeamInformation() throws URISyntaxException {
        // Create the team
        Team toCreate = new Team("ApprenTeam");

        URI creationUri = getUrlForRoute("/api/teams");

        ResponseEntity<TeamDto> createdResponse
                = restTemplate.postForEntity(creationUri, toCreate, TeamDto.class);

        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        TeamDto created = extractPayload(createdResponse);

        // Change its name
        UpdateTeamCommand updateTeamCommand = new UpdateTeamCommand();
        updateTeamCommand.setName(new Date().toString());

        URI updateUrl = getUrlForRoute("/api/teams/" + created.getId());

        restTemplate.put(updateUrl, updateTeamCommand);

        // Retrieve the team and assert that the name changed
        URI teamUrl = getUrlForRoute("/api/teams/" + created.getId());

        ResponseEntity<TeamDetailsDto> updatedResponse
                = restTemplate.getForEntity(teamUrl, TeamDetailsDto.class);

        assertEquals(updatedResponse.getStatusCode(), HttpStatus.OK);

        TeamDetailsDto updated = extractPayload(updatedResponse);
        assertEquals(updated.getName(), updateTeamCommand.getName());
    }

}
