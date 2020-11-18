package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user;

import eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Command to delete a user
 *
 * @see IUserCommandService
 */
@Getter @Setter @ToString @AllArgsConstructor
public class DeleteUserCommand {

    /**
     * Id of the user to delete
     */
    private long id;

}
