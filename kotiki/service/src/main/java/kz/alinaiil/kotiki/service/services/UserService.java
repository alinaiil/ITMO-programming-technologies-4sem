package kz.alinaiil.kotiki.service.services;

import kz.alinaiil.kotiki.service.dto.UserDto;

public interface UserService {
    UserDto createUser(String username, String password, String roleName, int ownerId);
}
