package eu.telecomnancy.membershipmanagement.api.integration.user;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.integration.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Case :
 *     (Write-only operation)
 *     Test the user creation
 *
 * @see TeamWriteRestController
 */
public class CreateUserWithAnInvalidPayloadTestCase extends IntegrationTest {

    /**
     * Ensure that the user creation with a blank firstname throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithABlankFirstname() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(22, TestUtils.BLANK_STRING, "Varnier");

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

    /**
     * Ensure that the user creation with a blank name throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithABlankName() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(23, "Pierre", TestUtils.BLANK_STRING);

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

    /**
     * Ensure that the user creation with an empty firstname throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithAnEmptyFirstname() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(22, TestUtils.EMPTY_STRING, "Varnier");

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

    /**
     * Ensure that the user creation with an empty name throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithAnEmptyName() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(23, "Pierre", TestUtils.EMPTY_STRING);

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

    /**
     * Ensure that the user creation with an age inferior to the minimum accepted throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithAnInferiorUnacceptedAge() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(User.AGE_MIN - 1, "Pierre", "Bouillon");

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

    /**
     * Ensure that the user creation with an age superior to the maximum accepted throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithASuperiorUnacceptedAge() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(User.AGE_MAX + 1, "Anakin", "Skywalker");

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

    /**
     * Ensure that the user creation with a too long firstname throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithATooLongFirstname() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(22, TestUtils.LONG_STRING, "Varnier");

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

    /**
     * Ensure that the user creation with a too long name throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithATooLongName() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(23, "Pierre", TestUtils.LONG_STRING);

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

    /**
     * Ensure that the user creation with an invalid age, firstname and name throws a 400 bad request
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createUserWithInvalidArgs() throws URISyntaxException {
        // Prepare the payload
        CreateUserCommand createUserCommand = new CreateUserCommand(User.AGE_MAX + 1, TestUtils.EMPTY_STRING, TestUtils.BLANK_STRING);

        // Perform the HTTP call
        URI uri = getUrlForRoute("/api/users");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(uri, createUserCommand, UserDto.class));
    }

}
