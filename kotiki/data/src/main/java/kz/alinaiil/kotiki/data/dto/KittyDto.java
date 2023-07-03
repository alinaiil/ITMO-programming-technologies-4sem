package kz.alinaiil.kotiki.data.dto;

import java.time.LocalDate;
import java.util.List;

public record KittyDto(int id, String name, LocalDate birthDate, String breed, String colour, int ownerId,
                       List<Integer> friendsId) {
}