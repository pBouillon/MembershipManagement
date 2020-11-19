package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Route :
 *     /api/users
 *
 * Case :
 *     (Read-only operation)
 *     Test getting all users when multiple users had been created
 *
 * @see UserReadRestController
 */
public class RetrieveUsersTestCase extends IntegrationTest {

    /**
     * Ensure that when we perform a GET /users with multiple users in the system, we retrieved a list containing
     * the users
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void getUsers() throws URISyntaxException {
        List<CreateUserCommand> usersToCreate = Arrays.asList(
                new CreateUserCommand(22,"Victor","Varnier"),
                new CreateUserCommand(23,"Pierre","Bouillon"),
                new CreateUserCommand(42,"Obi-Wan","Kenobi"));

        URI createUserUri = getUrlForRoute("/api/users");

        List<UserDto> createdUsers = usersToCreate.stream()
                .map(userToCreate
                        -> restTemplate
                        .postForEntity(createUserUri, userToCreate, UserDto.class)
                        .getBody())
                .collect(Collectors.toList());

        // Retrieve a list of users
        URI uri = getUrlForRoute("/api/users");

        // Perform the HTTP call
        ResponseEntity<List<UserDto>> createdResponse
                = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<>() {
        });

        // Ensure that the list of users is retrieved
        assertEquals(createdResponse.getStatusCode(), HttpStatus.OK);

        List<UserDto> retrievedUsers = createdResponse.getBody();
        assertNotNull(retrievedUsers);

        // Ensure that we retrieved a list containing all the users
        retrievedUsers.containsAll(createdUsers);
    }

}
