package eu.telecomnancy.membershipmanagement.api.controllers.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * Endpoint for: GET /teams
     *
     * Retrieve all teams of the system
     *
     * @return A JSON payload containing all the teams
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="Retrieve all teams tracked by the system")
    public ResponseEntity<List<TeamDto>> get() {
        List<Team> teams = teamService.getTeams();

        return ResponseEntity.ok()
                .body(mapper.toDtoList(teams));
    }

}
