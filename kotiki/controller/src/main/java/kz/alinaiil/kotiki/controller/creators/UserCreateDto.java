package kz.alinaiil.kotiki.controller.creators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record UserCreateDto(@NotBlank(message = "Username should not be blank") String username, @NotBlank(message = "Password should not be blank") String password, @NotBlank(message = "Role should not be blank") String roleName, int ownerId) {
}
