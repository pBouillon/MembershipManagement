package eu.telecomnancy.membershipmanagement.api.controllers.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamQueryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API controller for the Team resource
 * Used for read-only operations
 *
 * @see TeamRestController
 */
@RestController
@RequestMapping(
        path = "/api/teams",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Team", tags = { TeamRestController.CONTROLLER_TAG })
public class TeamReadRestController extends TeamRestController {

    /**
     * Team service used for read-only operation
     */
    private final ITeamQueryService teamService;

    /**
     * Default constructor
     *
     * @param teamService Team service used for read-only operation
     * @param mapper TeamDto mapper utility
     */
    @Autowired
    public TeamReadRestController(ITeamQueryService teamService, TeamMapper mapper) {
        super(mapper);

        this.teamService = teamService;
    }

}
