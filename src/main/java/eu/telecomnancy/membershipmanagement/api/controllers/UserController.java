package eu.telecomnancy.membershipmanagement.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Endpoint for: GET /users
     *
     * Retrieve all users of the system
     *
     * @return A JSON payload containing all the users
     */
    @GetMapping(path = "/users")
    @ApiOperation(value="Retrieve all users of the system", tags = { CONTROLLER_TAG })
    public ResponseEntity<Object> Get() {
        return ResponseEntity.ok()
                // Create anonymous class in order to have a properly serialized JSON as a result
                .body(new Object() {
                    public final String message = "Hello There !";
                });
    }

}
