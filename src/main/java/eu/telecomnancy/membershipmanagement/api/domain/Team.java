package eu.telecomnancy.membershipmanagement.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
     * Maximum name length of the team accepted by the system
     */
    public static final int NAME_MAX_LENGTH = 50;

    /**
     * Maximum name length of the team accepted by the system
     */
    public static final int NAME_MIN_LENGTH = 3;

    /**
     * Team id used for the persistence
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", insertable = false, nullable = false)
    private Long id;

    /**
     * Name of the team
     */
    private String name;

    /**
     * Members of the team
     */
    @OneToMany(mappedBy = "team")
    private List<User> members = new ArrayList<>();

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
