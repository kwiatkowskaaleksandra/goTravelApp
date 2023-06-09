package biuropodrozy.gotravel.mapper;

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.rest.dto.UserDto;
import org.springframework.stereotype.Service;

/**
 * The UserMapper implementation.
 */
@Service
public class UserMapperImpl implements UserMapper {

    /**
     * Transfer user to userDto.
     * @param user the user
     * @return the user dto
     */
    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole(), user.getPhoneNumber(), user.getCity(), user.getStreet(), user.getStreetNumber(), user.getZipCode());
    }
}
