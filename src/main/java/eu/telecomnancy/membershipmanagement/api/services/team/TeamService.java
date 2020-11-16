package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.TeamRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle {@link Team}-related operations
 */
@Log4j2
@Service
public class TeamService implements ITeamCommandService, ITeamQueryService {

    /**
     * TeamDto mapper utility
     */
    protected final TeamMapper mapper;

    /**
     * Repository to access the {@link Team} entity in the database
     */
    private final TeamRepository teamRepository;

    /**
     * Create a new instance of the TeamService
     *
     * @param teamRepository Repository to access the {@link Team} entity in the database
     */
    @Autowired
    public TeamService(TeamRepository teamRepository, TeamMapper mapper) {
        this.mapper = mapper;
        this.teamRepository = teamRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> getTeams() {
        List<Team> teams = teamRepository.findAll();

        log.info("Retrieved {} teams", teams.size());

        return teams;
    }

}
