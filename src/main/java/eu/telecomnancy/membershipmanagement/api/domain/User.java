package eu.telecomnancy.membershipmanagement.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Represent a user in the system
 */
@Entity(name = "users")
@Data @NoArgsConstructor
public class User implements Serializable {

    /**
     * User id used for the persistence
     */
    @Id
    @GeneratedValue
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

    /**
     * Create a user from its information
     *
     * @param age User's age
     * @param firstname User's firstname
     * @param name User's name
     */
    public User(int age, String firstname, String name) {
        this.age = age;
        this.firstname = firstname;
        this.name = name;
    }

}
