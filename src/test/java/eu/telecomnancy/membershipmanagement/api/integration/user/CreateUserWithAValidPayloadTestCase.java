package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Route :
 *     /api/teams
 *
 * Case :
 *     (Write-only operation)
 *     Test the user creation
 *
 * @see UserWriteRestController
 */
public class CreateUserWithAValidPayloadTestCase extends IntegrationTest {

    /**
     * Ensure that the user creation is functional
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithValidArgs() throws URISyntaxException {
        // Prepare the payload
        User toCreate = new User(42,"Mace","Windu");

        // Perform the HTTP call
        URI creationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdResponse
                = restTemplate.postForEntity(creationUri, toCreate, UserDto.class);

        // Ensure that the creation successfully happened
        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        UserDto created = extractPayload(createdResponse);

        assertEquals(created.getName(), toCreate.getName());
    }

}
