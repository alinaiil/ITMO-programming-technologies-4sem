package kz.alinaiil.kotiki.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record OwnerCreateDto(@NotBlank(message = "Name should not be blank") String name, @PastOrPresent(message = "Date is invalid") LocalDate birthDate) {
}
