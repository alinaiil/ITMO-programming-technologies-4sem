package kz.alinaiil.kotiki.service.services;

import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.dto.OwnerDto;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(String name, LocalDate birthDate);
    void addKitty(int ownerId, int kittyId);
    OwnerDto getOwnerById(int id);
    List<KittyDto> findAllKitties(int id);
    List<OwnerDto> findAllOwners();
    void removeOwner(int id);
}
