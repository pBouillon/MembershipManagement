package eu.telecomnancy.membershipmanagement.api.controllers.user;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.commands.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.dto.UserDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * API controller for the User resource
 * Used for write-only operations
 *
 * @see UserRestController
 */
@RestController
@RequestMapping(
        path = "/api/users",
        produces = "application/json",
        consumes = "application/json")
@Api(value = "User", tags = { UserRestController.CONTROLLER_TAG })
public class UserWriteRestController extends UserRestController {

    /**
     * User service used for write-only operation
     */
    private final IUserCommandService userService;

    /**
     * Default constructor
     *
     * @param userService User service used for write-only operation
     * @param mapper UserDto mapper utility
     */
    @Autowired
    public UserWriteRestController(IUserCommandService userService, UserMapper mapper) {
        super(mapper);

        this.userService = userService;
    }

    /**
     * Endpoint for: POST /users
     *
     * Create a new user with no team
     *
     * @param createUserCommand A JSON payload containing all the users
     * @return A JSON payload containing all the users
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Create a new user with no team")
    public ResponseEntity<UserDto> post(
            @ApiParam(value = "Payload from which creating the user")
            @Valid @RequestBody CreateUserCommand createUserCommand) {
        // Create the new user and retrieve the newly created one
        User created = userService.createUser(createUserCommand);

        // Return the result with its location
        return ResponseEntity.created(getUserLocation(created))
                .body(mapper.toDto(created));
    }

    /**
     * Create the user location
     *
     * @param user User to locate
     * @return The URI inthe API in which the resource is accessible
     */
    private URI getUserLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }

    /**
     * Endpoint for: PUT /users/:id
     *
     * Create or replace the user with the specified identifier
     * See: https://tools.ietf.org/html/rfc2616#page-55
     *
     * @return A {@link UserDto} along with the status of the resource: HTTP 200 OK if he has been updated; and
     * HTTP 201 CREATED if he has been created
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="Create or replace an existing user by its id")
    public ResponseEntity<UserDto> put(
            @ApiParam(value = "Id of the targeted user")
            @PathVariable long id,
            @ApiParam(value = "Payload from which the user details will be created or replaced")
            @Valid @RequestBody UpdateUserCommand updateUserCommand) {
        // Retrieve the new user and its creation status
        Pair<User, Boolean> updatedUserStatus = userService.createOrReplaceUser(id, updateUserCommand);

        // Extract the data from the pair
        User user = updatedUserStatus.getFirst();
        boolean isCreated = updatedUserStatus.getSecond();

        // Return HTTP 200 OK if the user has been updated
        if (!isCreated) {
            return ResponseEntity.ok(mapper.toDto(user));
        }

        // Return HTTP 201 CREATED if the user has just been created
        return ResponseEntity.created(getUserLocation(user))
                .body(mapper.toDto(user));
    }

}
