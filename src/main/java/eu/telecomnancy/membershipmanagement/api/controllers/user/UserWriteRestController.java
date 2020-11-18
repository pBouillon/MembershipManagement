package eu.telecomnancy.membershipmanagement.api.controllers.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.DeleteUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.PatchUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.user.UnknownUserException;
import eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService;
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
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
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
     * Endpoint for: DELETE /users/:id
     *
     * Delete a user with the specified identifier if it exists
     *
     * @return No content
     */
    @DeleteMapping(path = "/{id}",
            consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Delete a user",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<?> delete(
            @ApiParam(value = "Id of the targeted user")
            @PathVariable long id) {
        DeleteUserCommand command = new DeleteUserCommand(id);

        try {
            userService.deleteUser(command);
        } catch (UnknownUserException ex) {
            // Return HTTP 404 NOT FOUND if the user is not known by the system
            return ResponseEntity.notFound().build();
        }

        // Return HTTP 204 NO CONTENT on a successful deletion
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for: PATCH /users/:id
     *
     * Partially update a user with the specified identifier if it exists
     *
     * @return The JSON of the updated user as {@link UserDto}
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a user",
            description = """
                    The PATCH can be perform surgically by specifying only the fields that you would like to update.

                    Missing fields will be ignored""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully patched",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Malformed patch"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<?> patch(
            @ApiParam(value = "Id of the targeted user")
            @PathVariable long id,
            @ApiParam(value = "Fields to update")
            @Valid @RequestBody PatchUserCommand patchUserCommand) {
        // Retrieve the new user and its creation status
        User user;

        try {
            user = userService.patchUser(id, patchUserCommand);
        } catch (UnknownUserException ex) {
            // Return HTTP 404 NOT FOUND if the user is not known by the system
            return ResponseEntity.notFound().build();
        }

        // Return HTTP 200 OK if the user has been updated
        return ResponseEntity.ok(mapper.toDto(user));
    }

    /**
     * Endpoint for: POST /users
     *
     * Create a new user with no team
     *
     * @param createUserCommand A JSON payload containing the new user's data
     * @return A JSON payload containing all the users
     */
    @PostMapping
    @Operation(summary = "Create a new user with no team",
            description = """
                    The user's age must be greater than 0 and under 101.
                    He also must have both his names between 2 and 50 characters.
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully created",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Malformed body")
            })
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
    @Operation(summary = "Replace an existing user by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully updated",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "Malformed body"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<?> put(
            @ApiParam(value = "Id of the targeted user")
            @PathVariable long id,
            @ApiParam(value = "Payload from which the user details will be replaced")
            @Valid @RequestBody UpdateUserCommand updateUserCommand) {
        User user;

        try {
            user = userService.updateUser(id, updateUserCommand);
        } catch (UnknownUserException ex) {
            // Return HTTP 404 NOT FOUND if the user is not known by the system
            return ResponseEntity.notFound().build();
        }

        // Return HTTP 200 OK if the user has been updated
        return ResponseEntity.ok(mapper.toDto(user));
    }

}
