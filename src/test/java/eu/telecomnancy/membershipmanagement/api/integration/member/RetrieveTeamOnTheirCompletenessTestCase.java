package eu.telecomnancy.membershipmanagement.api.integration.member;

import eu.telecomnancy.membershipmanagement.api.controllers.team.TeamWriteRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserReadRestController;
import eu.telecomnancy.membershipmanagement.api.controllers.user.UserWriteRestController;
import eu.telecomnancy.membershipmanagement.api.integration.IntegrationTest;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

/**
 * Routes :
 *     /api/teams/
 *     /api/teams/:id/members
 *     /api/users/
 *
 * Case :
 *     (Read & Write operations)
 *     Test that the team filtering based on their isComplete attribute is functional
 *
 * @see TeamWriteRestController
 * @see UserReadRestController
 * @see UserWriteRestController
 */
public class RetrieveTeamOnTheirCompletenessTestCase extends IntegrationTest {

    /**
     * Ensure that the team filtering based on their isComplete attribute is functional
     *
     * @throws URISyntaxException Throws exception when the URI is invalid
     */
    @Test
    public void retrieveTeamOnTheirCompleteness() throws URISyntaxException {
        // Create two teams

        // Create enough user to fill a team

        // Ensure that the result of the queries with and without filtering is coherent

        // Fill a team

        // Ensure that the result of the queries with and without filtering is coherent

    }

}
