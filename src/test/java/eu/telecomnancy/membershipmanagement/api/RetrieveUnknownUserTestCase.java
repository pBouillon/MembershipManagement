package eu.telecomnancy.membershipmanagement.api;

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
 *     Test when an id that does not correspond to an existing user is provided
 *
 * @see UserReadRestController
 */
public class RetrieveUnknownUserTestCase extends IntegrationTest {

    /**
     * Ensure that when an unknown user is retrieved we get a 404 not found
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void getNonExistentUser() throws URISyntaxException {
        // Retrieve an unknown user
        final int unknownUserId = Integer.MAX_VALUE;
        URI uri = getUrlForRoute("/api/users/" + unknownUserId);

        // Perform the HTTP call
        Assertions.assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity(uri, UserDetailsDto.class));
    }

}
