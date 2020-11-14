package eu.telecomnancy.membershipmanagement.api.controllers.user;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.UserMapper;
import eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API controller for the User resource
 * Used for write-only operations
 *
 * @see UserRestController
 */
@RestController
@RequestMapping(
        path = "/api/users",
        produces = "application/json")
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

}
