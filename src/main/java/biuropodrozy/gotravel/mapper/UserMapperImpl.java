package biuropodrozy.gotravel.mapper;/*
 * @project gotravel
 * @author kola
 */

import biuropodrozy.gotravel.model.User;
import biuropodrozy.gotravel.rest.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
