package eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.CreateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.team.UpdateTeamCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Team and TeamDto mapper utility
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamMapper {

    /**
     * Convert a {@link Team} entity to a {@link TeamDto}
     *
     * @param team Entity to be converted
     * @return The associated DTO
     */
    @Mapping(target = "complete", expression = "java( team.isTeamComplete() )")
    TeamDto toDto(Team team);

    /**
     * Convert {@link Team} entities to a list of {@link TeamDto}
     *
     * @param teams Entities to be converted
     * @return A list containing the associated DTOs
     */
    List<TeamDto> toDtoList(List<Team> teams);

    /**
     * Convert a {@link CreateTeamCommand} to a {@link Team}
     *
     * @param command Command to be converted
     * @return The associated User
     */
    Team toTeam(CreateTeamCommand command);

    /**
     * Convert a {@link UpdateTeamCommand} to a {@link Team}
     *
     * @param command Command to be converted
     * @return The associated User
     */
    Team toTeam(UpdateTeamCommand command);

    /**
     * Replace the content of a {@link Team} by the values held by the {@link UpdateTeamCommand}
     *
     * @param command Payload holding the new values used to replace the existing ones
     * @param team Team to be replaced
     */
    void updateFromCommand(UpdateTeamCommand command, @MappingTarget Team team);

}
