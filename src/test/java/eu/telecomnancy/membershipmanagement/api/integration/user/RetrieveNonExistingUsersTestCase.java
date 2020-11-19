package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Route :
 *     /api/users
 *
 * Case :
 *     (Read-only operation)
 *     Test getting all users when no user had been created
 *
 * @see UserReadRestController
 */
public class RetrieveNonExistingUsersTestCase extends IntegrationTest {

    /**
     * Ensure that when we perform a GET /users with no user in the system, we retrieved an empty list
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void getNonExistentUsers() throws URISyntaxException {
        // Retrieve a list of unknown users
        URI uri = getUrlForRoute("/api/users");

        // Perform the HTTP call
        ResponseEntity<List<UserDto>> createdResponse
                = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<>() {
        });


        // Ensure that the list of users is retrieved
        assertEquals(createdResponse.getStatusCode(), HttpStatus.OK);

        List<UserDto> retrievedList = createdResponse.getBody();
        assertNotNull(retrievedList);

        // Ensure that we retrieved an empty list
        assertEquals(retrievedList, Collections.emptyList());
    }

}
