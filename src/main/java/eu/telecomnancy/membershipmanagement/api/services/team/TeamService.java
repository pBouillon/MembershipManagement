package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.*;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings.TeamMapper;
import eu.telecomnancy.membershipmanagement.api.dal.repositories.TeamRepository;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.TeamAlreadyCompleteException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.UnknownTeamException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.UserNotAMemberOfTheTeamException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.user.UnknownUserException;
import eu.telecomnancy.membershipmanagement.api.services.user.UserService;
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
     * Injected UserService used to update the membership of the users
     */
    private final UserService userService;

    /**
     * Create a new instance of the TeamService
     *
     * @param teamRepository Repository to access the {@link Team} entity in the database
     */
    @Autowired
    public TeamService(TeamRepository teamRepository, UserService userService, TeamMapper mapper) {
        this.mapper = mapper;
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Team addTeamMember(long teamId, CreateTeamMemberCommand command)
            throws UnknownTeamException, UnknownUserException {
        // Check if the team can have a new member
        Team team = retrieveTeamById(teamId);

        if (team.isComplete()) {
            log.error("The team {} is full and can't have any other member", team);
            throw new TeamAlreadyCompleteException();
        }

        // Add the user to the team members
        User user = userService.addToTeam(
                command.getMemberToAddId(), team);

        // Update the completeness of the team with this new member
        team.setComplete(team.isTeamComplete());
        teamRepository.save(team);

        log.info("User {} successfully added to the members of the team {}", user, team);

        // Return the result
        return team;
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
    public void deleteTeam(DeleteTeamCommand command)
            throws UnknownTeamException {
        // Retrieve the team to delete
        Team toDelete = retrieveTeamById(
                command.getTeamId());

        // Delete the membership of all of its members
        toDelete.getMembers()
                .stream()
                .map(User::getId)
                .forEach(userService::leaveTeam);

        // Clean the team's members
        toDelete.setMembers(null);
        teamRepository.save(toDelete);

        // Perform the deletion
        teamRepository.delete(toDelete);
        
        log.info("Successfully deleted team {}", toDelete);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeMemberFromTeam(DeleteTeamMemberCommand command)
            throws UnknownTeamException, UnknownUserException {
        // Retrieve the team and its members
        long memberId = command.getMemberId();
        Team team = retrieveTeamById(command.getTeamId());

        // Check if the user does belong to the team
        boolean isUserMemberOfTheTeam = team.getMembers()
                .stream()
                .anyMatch(user -> user.getId() == memberId);

        if (!isUserMemberOfTheTeam) {
            log.error(
                    "Unable to remove the user of id {} from the team {} because he does not belong to is",
                    memberId, team);
            throw new UserNotAMemberOfTheTeamException(memberId, team);
        }

        // Perform the removal
        userService.leaveTeam(memberId);

        // If the team was complete, then it is no longer so
        if (team.isComplete()) {
            team.setComplete(false);
            teamRepository.save(team);
        }

        log.info("The user of id {} has successfully been removed from the team {}", memberId, team);
    }

    /**
     * Try to retrieve a team by its id
     *
     * @param teamId Id of the team to check
     * @throws UnknownTeamException If there is no team for the provided id
     */
    public Team retrieveTeamById(long teamId)
            throws UnknownTeamException {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> {
                    log.error("Unknown team of id {}", teamId);
                    return new UnknownTeamException(teamId);
                });
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Team getTeam(GetTeamQuery getTeamQuery) {
        Team team = retrieveTeamById(getTeamQuery.getId());

        log.info("Successfully retrieved team {}", team);

        return team;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public Optional<Team> getTeamMembers(GetTeamMembersQuery getTeamMembersQuery)
            throws UnknownTeamException {
        long teamId = getTeamMembersQuery.getId();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);

        optionalTeam.ifPresentOrElse(
                team -> {
                    log.info("Retrieved team {} from id {}", team, teamId);
                    // Trigger the lazy loading
                    team.getMembers().size();
                },
                () -> log.warn("Unable to retrieve a team with id {}", teamId));

        return optionalTeam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> getTeams(GetTeamsQuery getTeamsQuery) {
        Optional<Boolean> isCompleteTeamFilter = getTeamsQuery.getIsComplete();

        isCompleteTeamFilter.ifPresent(value
                -> log.info("Retrieving all teams such that team.isComplete = {}", value));

        List<Team> teams = isCompleteTeamFilter.isPresent()
                ? teamRepository.getTeamByIsComplete(isCompleteTeamFilter.get())
                : teamRepository.findAll();

        log.info("Retrieved {} teams", teams.size());

        return teams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Team updateTeam(long teamId, UpdateTeamCommand command)
            throws UnknownTeamException {
        // Retrieve the team to update
        Team target = retrieveTeamById(teamId);

        // Perform the update
        log.info("Update the team {} to {}", target, command);

        mapper.updateFromCommand(command, target);

        log.info("Updated team: {}", target);

        // Return the saved instance
        return teamRepository.save(target);
    }

}
