package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Case :
 *     (Write-only operation)
 *     Test that the controller return the appropriate status code when deleting an unknown team
 *
 * @see TeamWriteRestController
 */
public class DeleteANonExistingTeamTestCase extends IntegrationTest {

    /**
     * Ensure that the appropriate status code is returned when deleting an unknown team
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void deleteAnUnknownTeam() throws URISyntaxException {
        final long unknownTeamId = 0;

        URI deleteTeamUri = getUrlForRoute("/api/teams/" + unknownTeamId);

        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restTemplate.delete(deleteTeamUri));
    }

}
