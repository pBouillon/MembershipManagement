package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Route :
 *     /api/users/:id
 *
 * Case :
 *     (Write-only operation)
 *     Test that the controller return the appropriate status code when deleting an unknown user
 *
 * @see UserWriteRestController
 */
public class DeleteANonExistingUserTestCase extends IntegrationTest {

    /**
     * Ensure that the appropriate status code is returned when deleting an unknown user
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void deleteAnUnknownUser() throws URISyntaxException {
        final long unknownUserId = 0;

        URI deleteUserUri = getUrlForRoute("/api/users/" + unknownUserId);

        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restTemplate.delete(deleteUserUri));
    }

}
