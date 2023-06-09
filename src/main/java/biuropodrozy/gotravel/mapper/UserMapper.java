package biuropodrozy.gotravel.mapper;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.rest.dto.UserDto;

/**
 * The interface UserMapper.
 */
public interface UserMapper {

    /**
     * Transfer user to userDto.
     *
     * @param user the user
     * @return the user dto
     */
    UserDto toUserDto(User user);
}
