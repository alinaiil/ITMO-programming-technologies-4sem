package kz.alinaiil.kotiki.service.mappers;

import kz.alinaiil.kotiki.data.models.User;
import kz.alinaiil.kotiki.service.dto.UserDto;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class UserMapper {
    public UserDto asDto(User user) {
        Objects.requireNonNull(user);
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole().toString(), user.getOwner().getId());
    }
}
