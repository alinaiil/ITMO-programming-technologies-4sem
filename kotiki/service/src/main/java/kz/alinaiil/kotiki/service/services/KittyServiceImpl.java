package kz.alinaiil.kotiki.service.services;

import kz.alinaiil.kotiki.data.dao.KittyDao;
import kz.alinaiil.kotiki.data.dao.OwnerDao;
import kz.alinaiil.kotiki.data.models.Breed;
import kz.alinaiil.kotiki.data.models.Colour;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.exceptions.KittyServiceException;
import kz.alinaiil.kotiki.service.mappers.KittyMapper;
import lombok.experimental.ExtensionMethod;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtensionMethod(KittyMapper.class)
public class KittyServiceImpl implements KittyService {
    private final KittyDao kittyDao;
    private final OwnerDao ownerDao;

    public KittyServiceImpl(KittyDao kittyDao, OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
        this.kittyDao = kittyDao;
    }

    public KittyDto createKitty(String name, LocalDate birthDate, String breedName, String colourName, int ownerId) {
        if (ownerDao.getById(ownerId) == null) {
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
        Kitty kitty = new Kitty(name, birthDate, breed, colour, ownerDao.getById(ownerId), new ArrayList<>());
        ownerDao.getById(ownerId).addKitty(kitty);
        kittyDao.add(kitty);
        return kitty.asDto();
    }

    public void makeFriends(int kittyId1, int kittyId2) {
        if (kittyDao.getById(kittyId1) == null) {
            throw KittyServiceException.noSuchKitty(kittyId1);
        }
        if (kittyDao.getById(kittyId2) == null) {
            throw KittyServiceException.noSuchKitty(kittyId2);
        }
        Kitty kitty1 = kittyDao.getById(kittyId1);
        Kitty kitty2 = kittyDao.getById(kittyId2);
        kitty1.addFriend(kitty2);
        kitty2.addFriend(kitty1);
        kittyDao.update(kitty1);
    }

    public void unfriendKitties(int kittyId1, int kittyId2) {
        if (kittyDao.getById(kittyId1) == null) {
            throw KittyServiceException.noSuchKitty(kittyId1);
        }
        if (kittyDao.getById(kittyId2) == null) {
            throw KittyServiceException.noSuchKitty(kittyId2);
        }
        Kitty kitty1 = kittyDao.getById(kittyId1);
        Kitty kitty2 = kittyDao.getById(kittyId2);
        kitty1.unfriend(kitty2);
        kitty2.unfriend(kitty1);
        kittyDao.update(kitty1);
    }

    public KittyDto getKittyById(int id) {
        if (kittyDao.getById(id) == null) {
            throw KittyServiceException.noSuchKitty(id);
        }
        return kittyDao.getById(id).asDto();
    }

    public List<KittyDto> findAllKitties() {
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyDao.getAll()) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<KittyDto> findAllFriends(int id) {
        if (kittyDao.getById(id) == null) {
            throw KittyServiceException.noSuchKitty(id);
        }
        List<KittyDto> friends = new ArrayList<>();
        for (Kitty friend : kittyDao.getAllFriends(id)) {
            friends.add(friend.asDto());
        }
        return friends;
    }

    public void removeKitty(int id) {
        if (kittyDao.getById(id) == null) {
            throw KittyServiceException.noSuchKitty(id);
        }
        for (Kitty k : kittyDao.getAllFriends(id)) {
            k.getFriends().remove(kittyDao.getById(id));
        }
        kittyDao.getById(id).getOwner().removeKitty(kittyDao.getById(id));
        kittyDao.remove(kittyDao.getById(id));
    }

    public List<KittyDto> findKittiesByBreed(String breedName) {
        Breed breed = Breed.valueOf(breedName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyDao.getByBreed(breed)) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<KittyDto> findKittiesByColour(String colourName) {
        Colour colour = Colour.valueOf(colourName);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyDao.getByColour(colour)) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }
}
