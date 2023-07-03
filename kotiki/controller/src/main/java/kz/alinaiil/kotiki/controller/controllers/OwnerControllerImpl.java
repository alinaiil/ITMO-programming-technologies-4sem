package kz.alinaiil.kotiki.controller.controllers;

import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.dto.OwnerDto;
import kz.alinaiil.kotiki.service.services.OwnerServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class OwnerControllerImpl implements OwnerController {
    private final OwnerServiceImpl ownerServiceImpl;

    public OwnerControllerImpl(OwnerServiceImpl ownerServiceImpl) {
        this.ownerServiceImpl = ownerServiceImpl;
    }

    public OwnerDto createOwner(String name, LocalDate birthDate) {
        return ownerServiceImpl.createOwner(name, birthDate);
    }

    public void addKitty(int ownerId, int kittyId) {
        ownerServiceImpl.addKitty(ownerId, kittyId);
    }

    public OwnerDto getOwnerById(int id) {
        return ownerServiceImpl.getOwnerById(id);
    }

    public List<KittyDto> findAllKitties(int id) {
        return ownerServiceImpl.findAllKitties(id);
    }

    public List<OwnerDto> findAllOwners() {
        return ownerServiceImpl.findAllOwners();
    }

    public void removeOwner(int id) {
        ownerServiceImpl.removeOwner(id);
    }
}
