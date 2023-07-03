package kz.alinaiil.kotiki.data.dto;

import java.time.LocalDate;
import java.util.List;

public record OwnerDto(int id, String name, LocalDate birthDate, List<KittyDto> kitties) {
}
