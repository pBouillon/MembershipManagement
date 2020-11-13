package eu.telecomnancy.membershipmanagement.api.dal.repositories;

import eu.telecomnancy.membershipmanagement.api.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to access the persisted `Person` entities
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> { }
