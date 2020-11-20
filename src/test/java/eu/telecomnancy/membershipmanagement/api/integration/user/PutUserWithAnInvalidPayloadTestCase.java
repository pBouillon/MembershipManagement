package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.integration.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Route :
 *     /api/users/:id
 *
 * Case :
 *     (Write operation)
 *     Test the user modification
 *
 * @see UserWriteRestController
 */
public class PutUserWithAnInvalidPayloadTestCase extends IntegrationTest {

    /**
     * Ensure that the user modification with a blank firstname throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithABlankFirstname() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(command.getAge());
        command.setFirstname(TestUtils.BLANK_STRING);
        command.setName(command.getName());

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

    /**
     * Ensure that the user modification with a blank name throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithABlankName() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(command.getAge());
        command.setFirstname(command.getFirstname());
        command.setName(TestUtils.BLANK_STRING);

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

    /**
     * Ensure that the user modification with an empty firstname throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithAnEmptyFirstname() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(command.getAge());
        command.setFirstname(TestUtils.EMPTY_STRING);
        command.setName(command.getName());

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

    /**
     * Ensure that the user modification with an empty name throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithAnEmptyName() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(command.getAge());
        command.setFirstname(command.getFirstname());
        command.setName(TestUtils.EMPTY_STRING);

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

    /**
     * Ensure that the user modification with an age inferior to the minimum accepted throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithAnInferiorUnacceptedAge() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(User.AGE_MIN-1);
        command.setFirstname(command.getFirstname());
        command.setName(command.getName());

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

    /**
     * Ensure that the user modification with an age superior to the maximum accepted throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithASuperiorUnacceptedAge() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(User.AGE_MAX+1);
        command.setFirstname(command.getFirstname());
        command.setName(command.getName());

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

    /**
     * Ensure that the user modification with a too long firstname throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithATooLongFirstname() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(command.getAge());
        command.setFirstname(TestUtils.LONG_STRING);
        command.setName(command.getName());

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

    /**
     * Ensure that the user modification with a too long name throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithATooLongName() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(command.getAge());
        command.setFirstname(command.getFirstname());
        command.setName(TestUtils.LONG_STRING);

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

    /**
     * Ensure that the user modification with an invalid age, firstname and name throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void putUserWithInvalidArgs() throws URISyntaxException {
        // Prepare the payload
        User userToCreate = new User(22, "Victor", "Varnier");

        // Perform the HTTP call
        URI userCreationUri = getUrlForRoute("/api/users");

        ResponseEntity<UserDto> createdUserResponse
                = restTemplate.postForEntity(userCreationUri, userToCreate, UserDto.class);

        assertEquals(createdUserResponse.getStatusCode(), HttpStatus.CREATED);

        UserDto createdUser = extractPayload(createdUserResponse);

        // Perform the HTTP call
        URI userModifiedUri = getUrlForRoute("/api/users/" + createdUser.getId());

        UpdateUserCommand command = new UpdateUserCommand();

        command.setAge(User.AGE_MAX+1);
        command.setFirstname(TestUtils.EMPTY_STRING);
        command.setName(TestUtils.BLANK_STRING);

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.put(userModifiedUri, command));
    }

}
