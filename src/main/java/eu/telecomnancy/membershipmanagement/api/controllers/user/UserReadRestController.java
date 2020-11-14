package eu.telecomnancy.membershipmanagement.api.controllers.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.controllers.dto.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.user.IUserQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API controller for the User resource
 * Used for read-only operations
 *
 * @see UserRestController
 */
@RestController
@RequestMapping(
        path = "/api/users",
        produces = "application/json")
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
    public ResponseEntity<List<UserDto>> Get() {
        List<User> users = userService.getUsers();

        return ResponseEntity.ok()
                .body(mapper.toDtoList(users));
    }

}
