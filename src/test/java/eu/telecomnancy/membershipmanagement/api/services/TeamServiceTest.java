package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.TeamRepository;
import eu.telecomnancy.membershipmanagement.api.services.team.TeamService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test suite for the TeamService
 *
 * @see TeamService
 */
@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    /**
     * Mocked Team repository to be injected for the unit tests
     */
    @Mock
    TeamRepository teamRepository;

    /**
     * Team mapper
     */
    TeamMapper mapper = Mappers.getMapper(TeamMapper.class);
    
}
