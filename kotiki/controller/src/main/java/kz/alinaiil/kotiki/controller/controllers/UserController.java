package kz.alinaiil.kotiki.controller.controllers;

import jakarta.validation.Valid;
import kz.alinaiil.kotiki.controller.creators.UserCreateDto;
import kz.alinaiil.kotiki.service.dto.UserDto;
import kz.alinaiil.kotiki.service.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping()
    public UserDto createUser(@Valid @RequestBody UserCreateDto userDto) {
        return userServiceImpl.createUser(userDto.username(), userDto.password(), userDto.roleName(), userDto.ownerId());
    }
}
