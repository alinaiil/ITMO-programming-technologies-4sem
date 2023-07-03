package kz.alinaiil.kotiki.service.services;

import kz.alinaiil.kotiki.data.models.Breed;
import kz.alinaiil.kotiki.data.models.Colour;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.repositories.KittyRepository;
import kz.alinaiil.kotiki.data.repositories.OwnerRepository;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.exceptions.KittyServiceException;
import kz.alinaiil.kotiki.service.mappers.KittyMapper;
import lombok.experimental.ExtensionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ExtensionMethod(KittyMapper.class)
public class KittyServiceImpl implements KittyService {
    private final KittyRepository kittyRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public KittyServiceImpl(KittyRepository kittyRepository, OwnerRepository ownerRepository) {
        this.kittyRepository = kittyRepository;
        this.ownerRepository = ownerRepository;
    }

    public KittyDto createKitty(String name, LocalDate birthDate, String breedName, String colourName, int ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw KittyServiceException.noSuchOwner(ownerId);
        }
        Breed breed = Breed.Unknown;
        for (int i = 0; i < Breed.values().length; i++) {
            if (Breed.values()[i].name().equals(breedName)) {
                breed = Breed.valueOf(breedName);
            }
        }
        Colour colour = null;
        for (int i = 0; i < Colour.values().length; i++) {
            if (Colour.values()[i].name().equals(colourName)) {
                colour = Colour.valueOf(colourName);
            }
        }
        if (colour == null) {
            throw KittyServiceException.noSuchColour(colourName);
        }
        Kitty kitty = new Kitty(name, birthDate, breed, colour, ownerRepository.findById(ownerId).orElseThrow(), new ArrayList<>());
        ownerRepository.findById(ownerId).ifPresent(owner -> owner.addKitty(kitty));
        kittyRepository.save(kitty);
        return kitty.asDto();
    }

    public void makeFriends(int kittyId1, int kittyId2) {
        if (!kittyRepository.existsById(kittyId1)) {
            throw KittyServiceException.noSuchKitty(kittyId1);
        }
        if (!kittyRepository.existsById(kittyId2)) {
            throw KittyServiceException.noSuchKitty(kittyId2);
        }

        Kitty kitty1 = kittyRepository.findById(kittyId1).orElseThrow();
        Kitty kitty2 = kittyRepository.findById(kittyId2).orElseThrow();
        kitty1.addFriend(kitty2);
        kitty2.addFriend(kitty1);
        kittyRepository.save(kitty1);
    }

    public void unfriendKitties(int kittyId1, int kittyId2) {
        if (!kittyRepository.existsById(kittyId1)) {
            throw KittyServiceException.noSuchKitty(kittyId1);
        }
        if (!kittyRepository.existsById(kittyId2)) {
            throw KittyServiceException.noSuchKitty(kittyId2);
        }
        Kitty kitty1 = kittyRepository.findById(kittyId1).orElseThrow();
        Kitty kitty2 = kittyRepository.findById(kittyId2).orElseThrow();
        kitty1.unfriend(kitty2);
        kittyRepository.save(kitty1);
        kitty2.unfriend(kitty1);
        kittyRepository.save(kitty2);
    }

    public KittyDto getKittyById(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyServiceException.noSuchKitty(id);
        }
        return kittyRepository.getReferenceById(id).asDto();
    }

    public List<KittyDto> findAllKitties() {
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<KittyDto> findAllFriends(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyServiceException.noSuchKitty(id);
        }
        List<KittyDto> friends = new ArrayList<>();
        for (Kitty friend : kittyRepository.getReferenceById(id).getFriends()) {
            friends.add(friend.asDto());
        }
        return friends;
    }

    public void removeKitty(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyServiceException.noSuchKitty(id);
        }
        Kitty kitty = kittyRepository.findById(id).orElseThrow();
        for (Kitty k : kitty.getFriends()) {
            k.getFriends().remove(kitty);
            kittyRepository.save(k);
        }
        kitty.getFriends().clear();
        kittyRepository.findById(id).orElseThrow().getOwner().removeKitty(kitty);
        kittyRepository.save(kitty);
        kittyRepository.deleteById(id);
    }

    public List<KittyDto> findKittiesByBreed(String breedName) {
        Breed breed = Breed.valueOf(breedName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getBreed().equals(breed)) {
                kitties.add(kitty.asDto());
            }
        }
        return kitties;
    }

    public List<KittyDto> findKittiesByColour(String colourName) {
        Colour colour = Colour.valueOf(colourName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getColour().equals(colour)) {
                kitties.add(kitty.asDto());
            }
        }
        return kitties;
    }

    @Override
    public List<KittyDto> findKittiesByColourAndBreed(String colourName, String breedName) {
        Colour colour = Colour.valueOf(colourName);
        Breed breed = Breed.valueOf(breedName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getColour().equals(colour) && kitty.getBreed().equals(breed)) {
                kitties.add(kitty.asDto());
            }
        }
        return kitties;
    }
}
