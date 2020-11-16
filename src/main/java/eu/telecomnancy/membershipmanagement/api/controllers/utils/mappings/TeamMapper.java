package eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.team.TeamDto;
import eu.telecomnancy.membershipmanagement.api.domain.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

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

}
