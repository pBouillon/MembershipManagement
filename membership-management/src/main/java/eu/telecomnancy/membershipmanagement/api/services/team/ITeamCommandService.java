package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.*;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.team.UnknownTeamException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.user.UnknownUserException;

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
     * Create a new team member from an existing user
     *
     * @param teamId Id of the team in which the member will be created
     * @param command Payload containing the data on the member to create
     * @return The team in its new state
     * @throws UnknownTeamException If the given id does not correspond to any stored {@link Team}
     * @throws UnknownUserException If the given id does not correspond to any stored {@link User}
     */
    Team addTeamMember(long teamId, CreateTeamMemberCommand command)
            throws UnknownTeamException, UnknownUserException;

    /**
     * Store a new {@link Team} in the database from the provided command
     *
     * @param command Payload to create the team
     * @return The team newly created
     */
    Team createTeam(CreateTeamCommand command);

    /**
     * Delete a team by its id
     *
     * @param command Command payload containing the data on the team to be removed
     * @throws UnknownTeamException If the given id does not correspond to any stored {@link Team}
     */
    void deleteTeam(DeleteTeamCommand command)
        throws UnknownTeamException;

    /**
     * Remove a user from the team
     *
     * @param command Payload containing the data on the member to be removed
     * @throws UnknownTeamException If the given id does not correspond to any stored {@link Team}
     * @throws UnknownUserException If the given id does not correspond to any stored {@link User}
     */
    void removeMemberFromTeam(DeleteTeamMemberCommand command)
            throws UnknownTeamException, UnknownUserException;

    /**
     * Given his id, rename an existing {@link Team}
     *
     * @param teamId Id of the targeted team
     * @param command Payload holding the team's new name
     * @return The team with the updated values
     * @throws UnknownTeamException If the given id does not correspond to any stored {@link Team}
     */
    Team patchTeam(long teamId, PatchTeamCommand command)
            throws UnknownTeamException;

}
