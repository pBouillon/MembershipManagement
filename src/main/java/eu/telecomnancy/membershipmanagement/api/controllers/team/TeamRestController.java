package eu.telecomnancy.membershipmanagement.api.controllers.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import lombok.AllArgsConstructor;

/**
 * Abstract Team controller
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 */
@AllArgsConstructor
public abstract class TeamRestController {

    /**
     * Controller-specific tag used to document the swagger endpoints
     */
    static final String CONTROLLER_TAG = "Team";

    /**
     * UserDto mapper utility
     */
    protected final TeamMapper mapper;

}
