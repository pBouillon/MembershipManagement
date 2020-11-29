package eu.telecomnancy.membershipmanagement.api.dal.repositories;

import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to access the persisted {@link User} entities
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieve all users if they don't belong to a team
     *
     * @return A list of user that doesn't belong to a team
     */
    List<User> findByTeamNull();

    /**
     * Retrieve all users if they belong to a team
     *
     * @return A list of user that belongs to a team
     */
    List<User> findByTeamNotNull();

}
