package eu.telecomnancy.membershipmanagement.api.controllers.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * User Data Transfer Object to be served and received by the API
 */
@Getter @Setter
public class UserDto {

    /**
     * Id of the user
     */
    private Long id;

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
