package kz.alinaiil.kotiki.service.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class OwnerDto {
    private final int id;
    private final String name;
    private final LocalDate birthDate;
    private final List<KittyDto> kitties;

    public OwnerDto(int id, String name, LocalDate birthDate, List<KittyDto> kitties) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.kitties = kitties;
    }
}
