package eu.telecomnancy.membershipmanagement.api.services.exceptions.team;

import eu.telecomnancy.membershipmanagement.api.services.exceptions.UnknownEntityException;

/**
 * Custom exception occurring when attempting to access a team that is not known by the system
 */
public class UnknownTeamException extends UnknownEntityException {

    /**
     * Create the exception from the id
     *
     * @param teamId Id the non-existing team
     */
    public UnknownTeamException(long teamId) {
        super(String.format("Unknown team of id %d", teamId));
    }

}
