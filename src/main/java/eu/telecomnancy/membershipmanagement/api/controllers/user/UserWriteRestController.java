package eu.telecomnancy.membershipmanagement.api.controllers.user;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.commands.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.dto.UserDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.UnknownUserException;
import eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        // Return the result with its location
        return ResponseEntity.created(location)
                .body(mapper.toDto(created));
    }

    /**
     * Endpoint for: PUT /users/:id
     *
     * Replace the user with the specified identifier if it exists
     *
     * @return The JSON of the updated user as {@link UserDto}
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="Replace an existing user by its id")
    public ResponseEntity<?> put(
            @ApiParam(value = "Id of the targeted user")
            @PathVariable long id,
            @ApiParam(value = "Payload from which the user details will be replaced")
            @Valid @RequestBody UpdateUserCommand updateUserCommand) {
        // Retrieve the new user and its creation status
        User user;

        try {
            user = userService.updateUser(id, updateUserCommand);
        } catch (UnknownUserException ex) {
            return ResponseEntity.notFound().build();
        }

        // Return HTTP 200 OK if the user has been updated
        return ResponseEntity.ok(mapper.toDto(user));
    }

}
