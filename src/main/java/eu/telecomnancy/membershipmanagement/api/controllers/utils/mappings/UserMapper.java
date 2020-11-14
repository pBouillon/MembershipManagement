package eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings;

import eu.telecomnancy.membershipmanagement.api.controllers.dto.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * User and UserDto mapper utility
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert a User entity to a User DTO
     *
     * @param user Entity to be converted
     * @return The associated DTO
     */
    UserDto toDto(User user);

    /**
     * Convert user entities to a User DTOs
     *
     * @param users Entities to be converted
     * @return A list containing the associated DTOs
     */
    List<UserDto> toDtoList(List<User> users);

}
