package eu.telecomnancy.membershipmanagement.api.controllers.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.GetUserQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.GetUsersQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.user.IUserQueryService;
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
 * API controller for the User resource
 * Used for read-only operations
 *
 * @see UserRestController
 */
@RestController
@RequestMapping(
        path = "/api/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "User", tags = { UserRestController.CONTROLLER_TAG })
public class UserReadRestController extends UserRestController {

    /**
     * User service used for read-only operation
     */
    private final IUserQueryService userService;

    /**
     * Default constructor
     *
     * @param userService User service used for read-only operation
     * @param mapper UserDto mapper utility
     */
    @Autowired
    public UserReadRestController(IUserQueryService userService, UserMapper mapper) {
        super(mapper);

        this.userService = userService;
    }

    /**
     * Endpoint for: GET /users
     *
     * Retrieve all users of the system
     *
     * Note: the `required = false`, even if not necessary thanks to the Optional type, is necessary for the swagger UI
     * If not present, Swagger will consider this parameter as mandatory
     *
     * @return A JSON payload containing all the users
     */
    @GetMapping
    @Operation(summary = "Retrieve all users tracked by the system",
             responses = {
                     @ApiResponse(responseCode = "200", description = "Users successfully retrieved")
             })
    public ResponseEntity<List<UserDto>> get(
            @ApiParam(value = "Optional parameter to filter the users regarding their belonging to a team")
            @RequestParam(required = false) Optional<Boolean> hasTeam) {
        GetUsersQuery getUsersQuery = new GetUsersQuery(hasTeam);

        List<User> users = userService.getUsers(getUsersQuery);

        return ResponseEntity.ok()
                .body(mapper.toDtoList(users));
    }

    /**
     * Endpoint for: GET /users/:id
     *
     * Retrieve an existing user by its id
     *
     * @param id Id of the user to retrieve
     * @return A JSON payload containing the user
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Retrieve an existing user by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully retrieved",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class)
                            )),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<UserDetailsDto> getUser(
            @ApiParam(value = "Id of the user to retrieve")
            @PathVariable long id) {
        GetUserQuery query = new GetUserQuery(id);

        User user = userService.getUser(query);

        return ResponseEntity.ok()
                .body(mapper.toDetailsDto(user));
    }

}
