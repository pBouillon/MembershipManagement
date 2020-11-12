package eu.telecomnancy.membershipmanagement.api.controllers;

import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API controller for the User resource
 */
@RestController
@RequestMapping(
        path = "/api",
        produces = "application/json")
@Api(value = "User", tags = { UserController.CONTROLLER_TAG })
public class UserController {

    /**
     * Controller-specific tag used to document the swagger endpoints
     */
    public static final String CONTROLLER_TAG = "User";

    /**
     * Service to handle user-related operations
     */
    private final UserService userService;

    /**
     * Default controller constructor
     *
     * @param userService Service to handle user-related operations
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for: GET /users
     *
     * Retrieve all users of the system
     *
     * @return A JSON payload containing all the users
     */
    @GetMapping(path = "/users")
    @ApiOperation(value="Retrieve all users of the system", tags = { CONTROLLER_TAG })
    public ResponseEntity<Object> Get() {
        List<User> users = userService.getUsers();

        return ResponseEntity.ok()
                .body(users);
    }

}
