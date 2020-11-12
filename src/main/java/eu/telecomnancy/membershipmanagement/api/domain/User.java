package eu.telecomnancy.membershipmanagement.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represent a user in the system
 */
@Getter @Setter @AllArgsConstructor
public class User {

    /**
     * Age of the user
     */
    public final int age;

    /**
     * Firstname of the user
     */
    public final String firstname;

    /**
     * Name of the user
     */
    public final String name;

}
