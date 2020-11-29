package eu.telecomnancy.membershipmanagement.api.controllers.utils.mappings;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.CreateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.PatchUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.user.UpdateUserCommand;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDetailsDto;
import eu.telecomnancy.membershipmanagement.api.controllers.utils.dto.user.UserDto;
import eu.telecomnancy.membershipmanagement.api.domain.User;
import eu.telecomnancy.membershipmanagement.api.services.user.UserService;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

/**
 * User and UserDto mapper utility
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    /**
     * Convert a {@link UpdateUserCommand} to a {@link CreateUserCommand}
     *
     * Used to create instead of replacing a {@link User}
     * in the {@link UserService}
     *
     * @param command Command to be converted
     * @return The associated command
     */
    CreateUserCommand toCreateUserCommand(UpdateUserCommand command);

    /**
     * Convert a {@link User} to a {@link UserDetailsDto}
     *
     * @param user Entity to be converted
     * @return The associated DTO
     */
    UserDetailsDto toDetailsDto(User user);

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
     * Convert a {@link PatchUserCommand} to a {@link User}
     * Optional.Empty fields will be converted to null
     *
     * @param command Command to be converted
     * @return The associated User
     */
    @Mapping(source = "age", target = "age", qualifiedByName = "unwrap")
    @Mapping(source = "firstname", target = "firstname", qualifiedByName = "unwrap")
    @Mapping(source = "name", target = "name", qualifiedByName = "unwrap")
    User toUser(PatchUserCommand command);

    /**
     * Replace the content of a {@link User} by the values held by the {@link UpdateUserCommand}
     *
     * @param command Payload holding the new values used to replace the existing ones
     * @param user User to be replaced
     */
    void updateFromCommand(UpdateUserCommand command, @MappingTarget User user);

    /**
     * Replace the content of a {@link User} by the values held by another {@link User}
     *
     * @param source User from which picking values
     * @param target User to be replaced
     */
    void updateFromUser(User source, @MappingTarget User target);

    /**
     * Custom MapStruct un-wrapper
     * see: https://stackoverflow.com/a/58313504/6152689
     *
     * @param optional Value of the optional type to unwrap
     * @param <T> Type of the optional target
     * @return The unwrapped value if present; null otherwise
     */
    @Named("unwrap")
    default <T> T unwrap(Optional<T> optional) {
        return optional.orElse(null);
    }

}
