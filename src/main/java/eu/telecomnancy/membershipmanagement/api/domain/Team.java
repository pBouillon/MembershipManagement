package eu.telecomnancy.membershipmanagement.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Represent a team in the system
 */
@Entity
@Data @NoArgsConstructor
public class Team {

    /**
     * Maximum number of members in the team
     */
    public static final int MAX_MEMBERS = 8;

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
     * Members of the team
     */
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> members;

    /**
     * Create a team from its information
     *
     * @param name Team's name
     */
    public Team(String name) {
        this.name = name;
    }

    /**
     * Check whether or not the team is complete
     *
     * @return true if the team is complete; false otherwise
     */
    public boolean isTeamComplete() {
        return members.size() == MAX_MEMBERS;
    }

}
