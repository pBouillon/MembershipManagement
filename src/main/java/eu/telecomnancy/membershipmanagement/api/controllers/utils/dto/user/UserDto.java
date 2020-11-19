package eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user;

import lombok.Data;

/**
 * User Data Transfer Object to be served and received by the API
 */
@Data
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
