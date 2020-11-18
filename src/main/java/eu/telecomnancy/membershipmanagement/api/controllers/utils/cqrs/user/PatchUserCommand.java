package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user;

import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * Command to partially update a new user
 * Since the update can be partial, all fields may or may not contain any value
 *
 * @see IUserCommandService
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter @Setter @ToString
public class PatchUserCommand {

    /**
     * Age of the user
     */
    private Optional<@Range(min = User.AGE_MIN, max = User.AGE_MAX) Integer> age
            = Optional.empty();

    /**
     * Firstname of the user
     */
    private Optional<@Size(min = User.NAME_MIN_LENGTH, max = User.NAME_MAX_LENGTH) String> firstname
            = Optional.empty();

    /**
     * Name of the user
     */
    private Optional<@Size(min = User.NAME_MIN_LENGTH, max = User.NAME_MAX_LENGTH) String> name
            = Optional.empty();

}
