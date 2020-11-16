package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.domain.Team;

import java.util.List;

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
     * Retrieve all teams of the application
     *
     * @return A list containing all of the teams
     */
    List<Team> getTeams();

}
