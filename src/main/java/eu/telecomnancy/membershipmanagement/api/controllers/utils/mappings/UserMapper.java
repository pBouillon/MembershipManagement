package eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings;

import eu.telecomnancy.membershipmanagement.api.controllers.commands.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.commands.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.dto.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * User and UserDto mapper utility
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert a {@link User} entity to a {@link UserDto}
     *
     * @param user Entity to be converted
     * @return The associated DTO
     */
    UserDto toDto(User user);

    /**
     * Convert {@link User} entities to a list of {@link UserDto}
     *
     * @param users Entities to be converted
     * @return A list containing the associated DTOs
     */
    List<UserDto> toDtoList(List<User> users);

    /**
     * Convert a {@link CreateUserCommand} to a {@link User}
     *
     * @param command Command to be converted
     * @return The associated User
     */
    User toUser(CreateUserCommand command);

    /**
     * Convert a {@link UpdateUserCommand} to a {@link CreateUserCommand}
     *
     * Used to create instead of replacing a {@link User}
     * in the {@link eu.telecomnancy.membershipmanagement.api.services.user.UserService}
     *
     * @param command Command to be converted
     * @return The associated command
     */
    CreateUserCommand toCreateUserCommand(UpdateUserCommand command);

    /**
     * Replace the content of a {@link User} by the values held by the {@link UpdateUserCommand}
     *
     * @param command Payload holding the new values used to replace the existing ones
     * @param user User to be replaced
     */
    void updateFromCommand(UpdateUserCommand command, @MappingTarget User user);

}
