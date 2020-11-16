package eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.commands;

import eu.telecomnancy.membershipmanagement.api.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Command to create or replace a new user
 *
 * @see eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService
 */
@Getter @Setter @ToString
public class UpdateUserCommand {

    /**
     * Age of the user
     */
    @Range(min = User.AGE_MIN, max = User.AGE_MAX)
    private int age;

    /**
     * Firstname of the user
     */
    @NotBlank
    @Size(min = User.NAME_MIN_LENGTH, max = User.NAME_MAX_LENGTH)
    private String firstname;

    /**
     * Name of the user
     */
    @NotBlank
    @Size(min = User.NAME_MIN_LENGTH, max = User.NAME_MAX_LENGTH)
    private String name;

}
