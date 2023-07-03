package kz.alinaiil.kotiki.service.services;

import kz.alinaiil.kotiki.data.models.Role;
import kz.alinaiil.kotiki.data.models.User;
import kz.alinaiil.kotiki.data.repositories.OwnerRepository;
import kz.alinaiil.kotiki.data.repositories.UserRepository;
import kz.alinaiil.kotiki.service.dto.UserDto;
import kz.alinaiil.kotiki.service.exceptions.UserServiceException;
import kz.alinaiil.kotiki.service.mappers.UserMapper;
import lombok.experimental.ExtensionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@ExtensionMethod(UserMapper.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, OwnerRepository ownerRepository) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
    }

    public UserDto createUser(String username, String password, String roleName, int ownerId) {
        if (userRepository.existsByUsername(username)) {
            throw UserServiceException.usernameIsTaken(username);
        }
        if (!ownerRepository.existsById(ownerId)) {
            throw UserServiceException.noSuchOwner(ownerId);
        }
        if (userRepository.findUserByOwner(ownerRepository.findById(ownerId).orElseThrow()).isPresent()) {
            throw UserServiceException.ownerAlreadyRegistered(ownerId);
        }
        Role role;
        if (roleName.equals(Role.ADMIN.name())) {
            role = Role.ADMIN;
        } else if (roleName.equals(Role.OWNER.name())) {
            role = Role.OWNER;
        } else {
            throw UserServiceException.noSuchRole(roleName);
        }
        User user = new User(username, encoder().encode(password), role, ownerRepository.findById(ownerId).orElseThrow());
        userRepository.save(user);
        return user.asDto();
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
