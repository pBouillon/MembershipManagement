package eu.telecomnancy.membershipmanagement.api.integration.team;

import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.integration.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Route :
 *     /api/teams
 *
 * Case :
 *     (Write-only operation)
 *     Test the team creation
 *
 * @see TeamWriteRestController
 */
public class CreateTeamWithAnInvalidPayloadTestCase extends IntegrationTest {

    /**
     * Ensure that the team creation does not accept a blank name
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createTeamWithABlankName() throws URISyntaxException {
        // Prepare the payload
        CreateTeamCommand createTeamCommand = new CreateTeamCommand(TestUtils.BLANK_STRING);

        // Ensure that the appropriate error is thrown
        URI creationUri = getUrlForRoute("/api/teams");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(creationUri, createTeamCommand, UserDto.class));
    }

    /**
     * Ensure that the team creation does not accept an empty name
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createTeamWithAnEmptyName() throws URISyntaxException {
        // Prepare the payload
        CreateTeamCommand createTeamCommand = new CreateTeamCommand(TestUtils.EMPTY_STRING);

        // Ensure that the appropriate error is thrown
        URI creationUri = getUrlForRoute("/api/teams");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(creationUri, createTeamCommand, UserDto.class));
    }

    /**
     * Ensure that the team creation does not accept a name that's considered too long
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void createTeamWithANameTooLong() throws URISyntaxException {
        // Prepare the payload
        CreateTeamCommand createTeamCommand = new CreateTeamCommand(TestUtils.LONG_STRING);

        // Ensure that the appropriate error is thrown
        URI creationUri = getUrlForRoute("/api/teams");

        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(creationUri, createTeamCommand, UserDto.class));
    }

}
