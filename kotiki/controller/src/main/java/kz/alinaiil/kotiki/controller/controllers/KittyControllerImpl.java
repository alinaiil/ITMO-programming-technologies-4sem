package kz.alinaiil.kotiki.controller.controllers;

import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.services.KittyServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class KittyControllerImpl implements KittyController {
    private final KittyServiceImpl kittyServiceImpl;

    public KittyControllerImpl(KittyServiceImpl kittyServiceImpl) {
        this.kittyServiceImpl = kittyServiceImpl;
    }

    public KittyDto createKitty(String name, LocalDate birthDate, String breed, String colour, int ownerId) {
        return kittyServiceImpl.createKitty(name, birthDate, breed, colour, ownerId);
    }

    public KittyDto getKittyById(int id) {
        return kittyServiceImpl.getKittyById(id);
    }

    public List<KittyDto> findAllFriends(int id) {
        return kittyServiceImpl.findAllFriends(id);
    }

    public List<KittyDto> findAllKitties() {
        return kittyServiceImpl.findAllKitties();
    }

    public void removeKitty(int id) {
        kittyServiceImpl.removeKitty(id);
    }

    public List<KittyDto> findKittyByBreed(String breed) {
        return kittyServiceImpl.findKittiesByBreed(breed);
    }

    public List<KittyDto> findKittiesByColour(String colour) {
        return kittyServiceImpl.findKittiesByColour(colour);
    }

    public void makeFriends(int kittyId1, int kittyId2) {
        kittyServiceImpl.makeFriends(kittyId1, kittyId2);
    }

    @Override
    public void unfriendKitties(int kittyId1, int kittyId2) {
        kittyServiceImpl.unfriendKitties(kittyId1, kittyId2);
    }
}
