package kz.alinaiil.kotiki.owners.mappers;

import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.dto.KittyDto;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class KittyMapper {
    public KittyDto asDto(Kitty kitty) {
        Objects.requireNonNull(kitty);
        List<Integer> friendsId = new ArrayList<>();
        for (Kitty k : kitty.getFriends()) {
            friendsId.add(k.getId());
        }
        return new KittyDto(kitty.getId(), kitty.getName(), kitty.getBirthDate(), kitty.getBreed().name(),
                kitty.getColour().name(), kitty.getOwner().getId(), friendsId);
    }
}
