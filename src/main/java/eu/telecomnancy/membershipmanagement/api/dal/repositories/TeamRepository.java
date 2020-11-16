package eu.telecomnancy.membershipmanagement.api.dal.repositories;

import eu.telecomnancy.membershipmanagement.api.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to access the persisted {@link Team} entities
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> { }
