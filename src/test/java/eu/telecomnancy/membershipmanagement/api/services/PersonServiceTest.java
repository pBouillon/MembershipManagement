package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.dal.repositories.PersonRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test suite for the PersonService
 *
 * @see PersonService
 */
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    /**
     * Mocked Person repository to be injected for the unit tests
     */
    @Mock
    PersonRepository personRepository;

    @Test
    public void givenAnEmptyDatabase_WhenQueryingAllUsers_ThenNoneShouldBeRetrieved() {
        // Arrange
        Mockito.when(personRepository.findAll())
                .thenReturn(new ArrayList<>());

        PersonService personService = new PersonService(personRepository);

        // Act
        List<Person> people = personService.getPeople();

        // Assert
        assertTrue(people.isEmpty());
    }

    @Test
    public void givenAPopulatedDatabase_WhenQueryingAllUsers_ThenAllShouldBeRetrieved() {
        // Arrange
        List<Person> storedPeople = Arrays.asList(
                new Person(23, "Pierre", "Bouillon"),
                new Person(23, "Victor", "Varnier")
        );

        Mockito.when(personRepository.findAll())
                .thenReturn(storedPeople);

        PersonService personService = new PersonService(personRepository);

        // Act
        List<Person> people = personService.getPeople();

        // Assert
        assertEquals(people, storedPeople);
    }

}
