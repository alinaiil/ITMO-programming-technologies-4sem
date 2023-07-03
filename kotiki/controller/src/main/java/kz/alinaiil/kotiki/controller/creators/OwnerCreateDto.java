package kz.alinaiil.kotiki.controller.creators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public record OwnerCreateDto(@NotBlank(message = "Name should not be blank") String name, @PastOrPresent(message = "Date is invalid") LocalDate birthDate) {
}