package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamMemberCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.UpdateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.UnknownTeamException;

/**
 * Command part of the TeamService
 * Specify the write-only commands
 *
 * The subsequent separation is made to respect the CQRS principles
 * see: https://www.baeldung.com/cqrs-for-a-spring-rest-api
 *
 * The returned values are the new state of the object, to be passed on to the controller
 * in order to respect the REST principles
 *
 * @see TeamService
 */
public interface ITeamCommandService {

    /**
     * Store a new {@link Team} in the database from the provided command
     *
     * @param command Payload to create the team
     * @return The team newly created
     */
    Team createTeam(CreateTeamCommand command);

    /**
     * Create a new team member from an existing user
     *
     * @param teamId Id of the team in which the member will be created
     * @param command Payload containing the data on the member to create
     * @return The team in its new state
     */
    Team createTeamMember(long teamId, CreateTeamMemberCommand command);

    /**
     * Given his id, replace the details of an existing {@link Team}
     *
     * @param teamId Id of the targeted team
     * @param command Payload holding the data to replace the existing ones
     * @return The team with the updated values
     * @throws UnknownTeamException If the given id does not correspond to any stored {@link Team}
     */
    Team updateTeam(long teamId, UpdateTeamCommand command)
            throws UnknownTeamException;

}
