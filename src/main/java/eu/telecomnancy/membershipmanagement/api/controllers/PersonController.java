package eu.telecomnancy.membershipmanagement.api.controllers;

import eu.telecomnancy.membershipmanagement.api.domain.Person;
import eu.telecomnancy.membershipmanagement.api.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API controller for the Person resource
 */
@RestController
@RequestMapping(
        path = "/api",
        produces = "application/json")
@Api(value = "Person", tags = { PersonController.CONTROLLER_TAG })
public class PersonController {

    /**
     * Controller-specific tag used to document the swagger endpoints
     */
    public static final String CONTROLLER_TAG = "Person";

    /**
     * Service to handle user-related operations
     */
    private final PersonService personService;

    /**
     * Default controller constructor
     *
     * @param personService Service to handle user-related operations
     */
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Endpoint for: GET /people
     *
     * Retrieve all people tracked by the system
     *
     * @return A JSON payload containing all the users
     */
    @GetMapping(path = "/people")
    @ApiOperation(value="Retrieve all persons tracked by the system", tags = { CONTROLLER_TAG })
    public ResponseEntity<List<Person>> Get() {
        List<Person> people = personService.getPeople();

        return ResponseEntity.ok()
                .body(people);
    }

}
