package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Route :
 *     /api/users/
 *
 * Case :
 *     (Read-only operation)
 *     Test when an invalid id is provided
 *
 * @see UserReadRestController
 */
public class RetrieveInvalidIdUserTestCase extends IntegrationTest {

    /**
     * Ensure that when an invalid id is provided we get a 404 not found
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void getUserWithInvalidId() throws URISyntaxException {
        // Retrieve a user with an invalid id
        final int invalidUserId = -5;
        URI uri = getUrlForRoute("/api/users/" + invalidUserId);

        // Perform the HTTP call
        Assertions.assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity(uri, UserDetailsDto.class));
    }
}
