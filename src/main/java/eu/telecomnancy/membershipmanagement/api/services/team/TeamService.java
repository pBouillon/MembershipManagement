package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.TeamRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Team createTeam(CreateTeamCommand createTeamCommand) {
         Team created = teamRepository.save(
                 mapper.toTeam(createTeamCommand));

         log.info("New team created {}", created);

         return created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Team> getTeam(GetTeamQuery getTeamQuery) {
        long teamId = getTeamQuery.getId();
        Optional<Team> optionalTeam= teamRepository.findById(teamId);

        optionalTeam.ifPresentOrElse(
                team -> log.info("Retrieved team {} from id {}", team, teamId),
                () -> log.warn("Unable to retrieve a team with id {}", teamId));

        return optionalTeam;
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
