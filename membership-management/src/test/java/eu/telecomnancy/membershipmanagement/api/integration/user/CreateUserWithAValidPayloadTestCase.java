package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
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
        CreateUserCommand createUserCommand = new CreateUserCommand(42, "Mace", "Windu");

        // Perform the HTTP call
        URI creationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdResponse
                = restTemplate.postForEntity(creationUri, createUserCommand, UserDto.class);

        // Ensure that the creation successfully happened
        assertEquals(createdResponse.getStatusCode(), HttpStatus.CREATED);
        UserDto created = extractPayload(createdResponse);

        assertEquals(created.getAge(), createUserCommand.getAge());
        assertEquals(created.getFirstname(), createUserCommand.getFirstname());
        assertEquals(created.getName(), createUserCommand.getName());
    }

}
