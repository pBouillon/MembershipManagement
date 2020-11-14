package eu.telecomnancy.membershipmanagement.api.controllers.commands;

import lombok.Getter;
import lombok.Setter;

/**
 * Command to create a new user
 *
 * @see eu.telecomnancy.membershipmanagement.api.services.user.IUserCommandService
 */
@Getter @Setter
public class CreateUserCommand {

    /**
     * Age of the user
     */
    private int age;

    /**
     * Firstname of the user
     */
    private String firstname;

    /**
     * Name of the user
     */
    private String name;

}
