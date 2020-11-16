package eu.telecomnancy.membershipmanagement.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represent a team in the system
 */
@Entity
@Data @NoArgsConstructor
public class Team {

    /**
     * Team id used for the persistence
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name of the team
     */
    private String name;

    /**
     * Create a team from its information
     *
     * @param name Team's name
     */
    public Team(String name) {
        this.name = name;
    }

}
