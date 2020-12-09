package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Case :
 *     (Write-only operation)
 *     Test that the user does not exists anymore when deleted
 *
 * @see UserWriteRestController
 */
public class DeleteAnExistingUserTestCase extends IntegrationTest {

    /**
     * Ensure that the user does not exists anymore when deleted
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void deleteAnExistingUser() throws URISyntaxException {
        // Create the user to be used
        CreateUserCommand createUserCommand = new CreateUserCommand(33, "Ki-Adi", "Mundi");

        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, createUserCommand, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Delete the user
        URI userDeletionUri = getUrlForRoute("/api/users/" + createdUser.getId());

        restTemplate.delete(userDeletionUri);

        // Ensure that the user does not exist anymore
        URI userUri = getUrlForRoute("/api/users/" + createdUser.getId());

        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity(userUri, UserDetailsDto.class));
    }

}
