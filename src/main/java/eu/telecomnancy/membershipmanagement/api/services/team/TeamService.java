package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.UpdateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.TeamRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.UnknownTeamException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.user.UnknownUserException;
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
    public Team createTeamMember(long teamId, CreateTeamMemberCommand command) {
        // TODO: creation logic
        return new Team();
    }

    /**
     * Check whether or not a team exists at the given id
     *
     * @param teamId Id of the team to check
     * @throws UnknownTeamException If there is no team for the provided id
     */
    private void ensureTeamIsExisting(long teamId)
            throws UnknownUserException {
        if (teamRepository.existsById(teamId)) {
            return;
        }

        // If the user does not exists, throw an exception
        log.error("Unknown team of id {}", teamId);
        throw new UnknownTeamException(teamId);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Team updateTeam(long teamId, UpdateTeamCommand command)
            throws UnknownTeamException {
        // If the user does not exists, throw an exception
        ensureTeamIsExisting(teamId);

        // Retrieve the user to update
        Team target = teamRepository.getOne(teamId);

        // Perform the update
        log.info("Update the team {} to {}", target, command);

        mapper.updateFromCommand(command, target);

        log.info("Updated team: {}", target);

        // Return the saved instance
        return teamRepository.save(target);
    }

}
