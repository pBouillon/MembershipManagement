package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Route :
 *     /api/users/:id
 *
 * Case :
 *     (Read & Write operation)
 *     Test that the controller successfully retrieve an existing user
 *
 * @see UserReadRestController
 */
public class RetrieveAUserTestCase extends IntegrationTest {

    /**
     * Ensure that if a user has been created, we can retrieve it
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void retrieveACreatedUser() throws URISyntaxException {
        // Create a new user in the system
        User created = new User(22, "Victor", "Varnier");

        URI creationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdResponse
                = restTemplate.postForEntity(creationUri, created, UserDto.class);

        // Ensure that the user is created and retrieve its id
        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);

        assertNotNull(createdResponse.getBody());
        created.setId(createdResponse.getBody().getId());

        // Perform the HTTP call to retrieve the details of the created user based on its id
        URI retrieveUri = getUrlForRoute("/api/users/" + created.getId());

        ResponseEntity<UserDetailsDto> retrievedResponse
                = restTemplate.getForEntity(retrieveUri, UserDetailsDto.class);

        // Ensure that the user retrieved is indeed the same as the one we created
        assertEquals(retrievedResponse.getStatusCode(), HttpStatus.OK);

        UserDetailsDto retrieved = extractPayload(retrievedResponse);

        assertEquals(retrieved.getId(), created.getId());
        assertEquals(retrieved.getAge(), created.getAge());
        assertEquals(retrieved.getFirstname(), created.getFirstname());
        assertEquals(retrieved.getName(), created.getName());

        // On creation, the user should not belong to any team
        assertNull(retrieved.getTeam());
    }

}
