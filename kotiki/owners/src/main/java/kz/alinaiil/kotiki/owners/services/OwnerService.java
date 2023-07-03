package kz.alinaiil.kotiki.owners.services;

import kz.alinaiil.kotiki.data.dto.OwnerCreateDto;
import kz.alinaiil.kotiki.data.dto.KittyDto;
import kz.alinaiil.kotiki.data.dto.OwnerDto;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(OwnerCreateDto ownerCreateDto);
    void getOwnerById(int id);
    void findAllKitties(int id);
    void findAllOwners(int trash);
    void removeOwner(int id);
}
