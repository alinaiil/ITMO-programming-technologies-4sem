package kz.alinaiil.kotiki.owners.mappers;

import kz.alinaiil.kotiki.data.dto.KittyDto;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.models.Owner;
import kz.alinaiil.kotiki.data.dto.OwnerDto;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
@ExtensionMethod(KittyMapper.class)
public class OwnerMapper {
    public OwnerDto asDto(Owner owner) {
        Objects.requireNonNull(owner);
        List<KittyDto> kittiesDto = new ArrayList<>();
        for (Kitty kitty : owner.getKitties()) {
            kittiesDto.add(kitty.asDto());
        }
        return new OwnerDto(owner.getId(), owner.getName(), owner.getBirthDate(), kittiesDto);
    }
}
