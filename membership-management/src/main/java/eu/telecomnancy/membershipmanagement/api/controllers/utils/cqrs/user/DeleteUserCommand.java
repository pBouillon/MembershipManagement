package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user;

import eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService;
import lombok.*;

/**
 * Command to delete a user
 *
 * @see IUserCommandService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserCommand {

    /**
     * Id of the user to delete
     */
    private long id;

}
