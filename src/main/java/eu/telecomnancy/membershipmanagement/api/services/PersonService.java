package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.dal.repositories.PersonRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle user-related operations
 */
@Log4j2
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
        List<Person> people = personRepository.findAll();

        log.info("PersonService.getPeople : retrieved {} people", people.size());

        return people;
    }

}
