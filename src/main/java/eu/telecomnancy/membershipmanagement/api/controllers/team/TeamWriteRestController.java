package eu.telecomnancy.membershipmanagement.api.controllers.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.team.ITeamCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * API controller for the Team resource
 * Used for write-only operations
 *
 * @see TeamRestController
 */
@RestController
@RequestMapping(
        path = "/api/teams",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Team", tags = { TeamRestController.CONTROLLER_TAG })
public class TeamWriteRestController extends TeamRestController {

    /**
     * Team service used for write-only operation
     */
    private final ITeamCommandService teamService;

    /**
     * Default constructor
     *
     * @param teamService Team service used for write-only operation
     * @param mapper TeamDto mapper utility
     */
    @Autowired
    public TeamWriteRestController(ITeamCommandService teamService, TeamMapper mapper) {
        super(mapper);

        this.teamService = teamService;
    }

    /**
     * Endpoint for: POST /teams
     *
     * Create a new team with no member
     *
     * @param createTeamCommand A JSON payload containing the new team's data
     * @return A JSON payload containing all the users
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Create team with no member")
    public ResponseEntity<TeamDto> post(
            @ApiParam(value = "Payload from which creating the team")
            @Valid @RequestBody CreateTeamCommand createTeamCommand) {
        // Create the new team and retrieve the newly created one
        Team created = teamService.createTeam(createTeamCommand);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        // Return the result with its location
        return ResponseEntity.created(location)
                .body(mapper.toDto(created));
    }


}
