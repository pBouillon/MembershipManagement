package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Case :
 *     (Read-only operation)
 *     Test when an id that does not correspond to an existing team is provided
 *
 * @see TeamReadRestController
 */
public class RetrieveUnknownTeamTestCase extends IntegrationTest {

    /**
     * Ensure that when an unknown team is retrieved we get a 404 not found
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void getNonExistentTeam() throws URISyntaxException {
        // Retrieve an unknown team
        final int unknownTeamId = Integer.MAX_VALUE;
        URI uri = getUrlForRoute("/api/teams/" + unknownTeamId);

        // Perform the HTTP call
        Assertions.assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity(uri, TeamDetailsDto.class));
    }

}
