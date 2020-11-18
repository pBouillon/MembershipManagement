package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamMembersQuery;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.GetTeamQuery;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.UnknownTeamException;

import java.util.List;
import java.util.Optional;

/**
 * Query part of the TeamService
 * Specify the read-only commands
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 *
 * @see TeamService
 */
public interface ITeamQueryService {

    /**
     * Retrieve a team by its id
     *
     * @param getTeamQuery Payload from which performing the search to retrieve a team
     * @return An optional team if it exists, no content if not
     * @throws UnknownTeamException If the given id does not correspond to any stored {@link Team}
     */
    Optional<Team> getTeam(GetTeamQuery getTeamQuery)
            throws UnknownTeamException;

    /**
     * Retrieve a team and its members by its id
     *
     * @param getTeamMembersQuery Payload from which retrieving the id of the queried team
     * @return The team along with its members
     * @throws UnknownTeamException If the given id does not correspond to any stored {@link Team}
     */
    Optional<Team> getTeamMembers(GetTeamMembersQuery getTeamMembersQuery)
            throws UnknownTeamException;

    /**
     * Retrieve all teams of the application
     *
     * @return A list containing all of the teams
     */
    List<Team> getTeams();

}
