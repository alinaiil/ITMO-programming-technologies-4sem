package kz.alinaiil.kotiki.service.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class KittyDto {
    private final int id;
    private final String name;
    private final LocalDate birthDate;
    private final String breed;
    private final String colour;
    private final int ownerId;
    private final List<Integer> friendsId;

    public KittyDto(int id, String name, LocalDate birthDate, String breed, String colour, int ownerId, List<Integer> friendsId) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.colour = colour;
        this.ownerId = ownerId;
        this.friendsId = friendsId;
    }
}
