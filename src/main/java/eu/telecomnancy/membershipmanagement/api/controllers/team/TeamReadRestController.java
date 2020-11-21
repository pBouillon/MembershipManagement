package eu.telecomnancy.membershipmanagement.api.controllers.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamMembersQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamsQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamMembersDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Retrieve an existing team by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Team successfully retrieved",
                            content = @Content(
                                    schema = @Schema(implementation = TeamDto.class)
                            )),
                    @ApiResponse(responseCode = "404", description = "Team not found")
            })
    public ResponseEntity<TeamDetailsDto> getTeam(
            @ApiParam(value = "Id of the team to retrieve")
            @PathVariable long id) {
        GetTeamQuery query = new GetTeamQuery(id);

        Team team = teamService.getTeam(query);

        return ResponseEntity.ok()
                .body(teamMapper.toDetailsDto(team));
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
    @Operation(summary = " Retrieve the team members by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Members successfully retrieved",
                            content = @Content(
                                    schema = @Schema(implementation = TeamMembersDto.class)
                            )),
                    @ApiResponse(responseCode = "404", description = "Team not found")
            })
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
    @Operation(summary = "Retrieve all teams of the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teams successfully retrieved")
            })
    public ResponseEntity<List<TeamDto>> get(
            @ApiParam(value = "Optional parameter to filter the teams regarding their completed attribute")
            @RequestParam Optional<Boolean> isComplete) {
        GetTeamsQuery getTeamsQuery = new GetTeamsQuery(isComplete);

        List<Team> teams = teamService.getTeams(getTeamsQuery);

        return ResponseEntity.ok()
                .body(teamMapper.toDtoList(teams));
    }

}
