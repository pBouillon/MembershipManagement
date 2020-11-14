package eu.telecomnancy.membershipmanagement.api.controllers.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import lombok.AllArgsConstructor;

/**
 * Abstract User controller
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 */
@AllArgsConstructor
public abstract class UserRestController {

    /**
     * Controller-specific tag used to document the swagger endpoints
     */
    static final String CONTROLLER_TAG = "User";

    /**
     * UserDto mapper utility
     */
    protected final UserMapper mapper;

}
