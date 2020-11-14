package eu.telecomnancy.membershipmanagement.api.dal.repositories;

import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to access the persisted User entities
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
