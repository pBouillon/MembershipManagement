package eu.telecomnancy.membershipmanagement.api.controllers.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.GetUserQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.user.IUserQueryService;
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
     * @return A JSON payload containing all the users
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="Retrieve all users tracked by the system")
    public ResponseEntity<List<UserDto>> get() {
        List<User> users = userService.getUsers();

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
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="Retrieve an existing user by its id")
    public ResponseEntity<User> getUser(
            @ApiParam(value = "Id of the user to retrieve")
            @PathVariable long id) {
        GetUserQuery query = new GetUserQuery(id);

        Optional<User> optionalUser = userService.getUser(query);

        return optionalUser
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
