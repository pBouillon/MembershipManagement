package eu.telecomnancy.membershipmanagement.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represent a user in the system
 */
@Entity(name = "users")
@Data @NoArgsConstructor
public class User implements Serializable {

    /**
     * Maximum age of the user accepted by the system
     */
    public static final int AGE_MAX = 110;

    /**
     * Minimal age of the user accepted by the system
     */
    public static final int AGE_MIN = 0;

    /**
     * Maximum name length of the user accepted by the system
     */
    public static final int NAME_MAX_LENGTH = 50;

    /**
     * Minimum name length of the user accepted by the system
     */
    public static final int NAME_MIN_LENGTH = 2;

    /**
     * User id used for the persistence
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, nullable = false)
    private Long id;

    /**
     * Age of the user
     */
    private Integer age;

    /**
     * Firstname of the user
     */
    private String firstname;

    /**
     * Name of the user
     */
    private String name;

    /**
     * User's team
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Team team;

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

    /**
     * Check whether or not the user is part of a team
     *
     * @return true if the user has a team; false otherwise
     */
    public boolean isMemberOfATeam() {
        return team != null;
    }

}
