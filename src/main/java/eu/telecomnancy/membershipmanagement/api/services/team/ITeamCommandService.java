package eu.telecomnancy.membershipmanagement.api.services.team;

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
public interface ITeamCommandService { }
