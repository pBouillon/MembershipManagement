package eu.telecomnancy.membershipmanagement.api.dal.repositories;

import eu.telecomnancy.membershipmanagement.api.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to access the persisted {@link Team} entities
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * Retrieve all teams with their complete attribute matching the filter
     *
     * @param isComplete Whether the team should be complete or not
     * @return A list of all teams matching the isComplete filter
     */
    List<Team> getTeamByIsComplete(boolean isComplete);

}
