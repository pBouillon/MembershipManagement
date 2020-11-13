package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.dal.repositories.PersonRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle user-related operations
 */
@Service
public class PersonService {

    /**
     * Repository to access the `Person` entity in the database
     */
    private final PersonRepository personRepository;

    /**
     * Create a new instance of the PersonService
     *
     * @param personRepository Repository to access the `Person` entity in the database
     */
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Retrieve all users of the application
     *
     * @return A list containing all of the users
     */
    public List<Person> getPeople() {
        return personRepository.findAll();
    }

}
