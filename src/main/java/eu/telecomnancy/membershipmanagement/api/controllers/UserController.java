package eu.telecomnancy.membershipmanagement.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for: api/users/
 *
 * API controller for the User resource
 */
@RestController
@RequestMapping(
        path = "/api/users",
        produces = "application/json")
public class UserController {

    /**
     * Endpoint for: GET /
     *
     * Retrieve all users of the system
     *
     * @return A JSON payload containing all the users
     */
    @GetMapping(path = "/")
    public ResponseEntity<Object> Get() {
        return ResponseEntity.ok()
                // Create anonymous class in order to have a properly serialized JSON as a result
                .body(new Object() {
                    public final String message = "Hello There !";
                });
    }

}
