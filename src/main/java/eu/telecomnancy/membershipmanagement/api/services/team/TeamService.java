package eu.telecomnancy.membershipmanagement.api.services.team;

import eu.telecomnancy.membershipmanagement.api.domain.Team;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Service to handle {@link Team}-related operations
 */
@Log4j2
@Service
public class TeamService implements ITeamCommandService, ITeamQueryService { }
