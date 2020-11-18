package eu.telecomnancy.membershipmanagement.api.controllers.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamMembersDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
     * Endpoint for: GET /teams/:id
     *
     * Retrieve a team by its id
     *
     * @param id Id of the team to retrieve
     * @return A JSON payload containing the team
     */
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="Retrieve a team by its id")
    public ResponseEntity<TeamDetailsDto> getTeam(
            @ApiParam(value = "Id of the team to retrieve")
            @PathVariable long id) {
        GetTeamQuery query = new GetTeamQuery(id);

        Optional<Team> optionalTeam = teamService.getTeam(query);

        return optionalTeam
                .map(team -> ResponseEntity.ok()
                        .body(mapper.toDetailsDto(team)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint for: GET /teams/:id/members
     *
     * Retrieve the team members by its id
     *
     * @param id Id of the team in which the members to retrieve are
     * @return A JSON payload containing the team
     */
    @GetMapping(path = "/{id}/members")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="Retrieve a team by its id",
            response = TeamMembersDto.class)
    public ResponseEntity<?> getTeamMembers(
            @ApiParam(value = "Id of the team in which the members to retrieve are")
            @PathVariable long id) {
        return ResponseEntity.ok(new TeamMembersDto());
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
