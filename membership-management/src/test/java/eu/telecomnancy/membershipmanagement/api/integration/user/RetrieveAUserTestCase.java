package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
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
        CreateUserCommand createUserCommand = new CreateUserCommand(22, "Victor", "Varnier");

        URI creationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdResponse
                = restTemplate.postForEntity(creationUri, createUserCommand, UserDto.class);

        // Ensure that the user is created and retrieve its id
        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        UserDto created = extractPayload(createdResponse);

        // Perform the HTTP call to retrieve the details of the created user based on its id
        URI retrieveUri = getUrlForRoute("/api/users/" + created.getId());

        ResponseEntity<UserDetailsDto> retrievedResponse
                = restTemplate.getForEntity(retrieveUri, UserDetailsDto.class);

        // Ensure that the user retrieved is indeed the same as the one we created
        assertEquals(retrievedResponse.getStatusCode(), HttpStatus.OK);

        UserDetailsDto retrieved = extractPayload(retrievedResponse);

        assertEquals(retrieved.getAge(), createUserCommand.getAge());
        assertEquals(retrieved.getFirstname(), createUserCommand.getFirstname());
        assertEquals(retrieved.getName(), createUserCommand.getName());

        // On creation, the user should not belong to any team
        assertNull(retrieved.getTeam());
    }

}
