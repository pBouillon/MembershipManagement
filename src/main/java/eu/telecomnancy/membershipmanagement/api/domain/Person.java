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
@Entity
@Data @NoArgsConstructor
public class Person implements Serializable {

    /**
     * Person id used for the persistence
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
     * @param age Person's age
     * @param firstname Person's firstname
     * @param name Person's name
     */
    public Person(int age, String firstname, String name) {
        this.age = age;
        this.firstname = firstname;
        this.name = name;
    }

}
