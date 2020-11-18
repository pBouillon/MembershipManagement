package eu.telecomnancy.membershipmanagement.api.controllers.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamMembersQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamMembersDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
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
     * @param teamMapper TeamDto mapper utility
     * @param userMapper UserDto mapper utility
     */
    @Autowired
    public TeamReadRestController(ITeamQueryService teamService, TeamMapper teamMapper, UserMapper userMapper) {
        super(teamMapper, userMapper);

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

        return teamService.getTeam(query)
                .map(team -> ResponseEntity.ok()
                        .body(teamMapper.toDetailsDto(team)))
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
    @ApiOperation(value="Retrieve the team members by its id",
            response = TeamMembersDto.class)
    public ResponseEntity<?> getTeamMembers(
            @ApiParam(value = "Id of the team in which the members to retrieve are")
            @PathVariable long id) {
        GetTeamMembersQuery query = new GetTeamMembersQuery(id);

        return teamService.getTeamMembers(query)
                .map(team -> ResponseEntity.ok()
                        .body(userMapper.toDtoList(team.getMembers())))
                .orElseGet(() -> ResponseEntity.notFound().build());
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
                .body(teamMapper.toDtoList(teams));
    }

}
