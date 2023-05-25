package biuropodrozy.gotravel.Mapper;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.Model.User;
import biuropodrozy.gotravel.Rest.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole(), user.getPhoneNumber(), user.getCity(), user.getStreet(), user.getStreetNumber(), user.getZipCode());
    }
}
