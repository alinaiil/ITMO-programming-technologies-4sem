package kz.alinaiil.kotiki.service.services;

import kz.alinaiil.kotiki.data.dao.KittyDao;
import kz.alinaiil.kotiki.data.dao.OwnerDao;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.models.Owner;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.dto.OwnerDto;
import kz.alinaiil.kotiki.service.exceptions.OwnerServiceException;
import kz.alinaiil.kotiki.service.mappers.KittyMapper;
import kz.alinaiil.kotiki.service.mappers.OwnerMapper;
import lombok.experimental.ExtensionMethod;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtensionMethod({OwnerMapper.class, KittyMapper.class})
public class OwnerServiceImpl implements OwnerService {
    private final KittyDao kittyDao;
    private final OwnerDao ownerDao;

    public OwnerServiceImpl(KittyDao kittyDao, OwnerDao ownerDao) {
        this.kittyDao = kittyDao;
        this.ownerDao = ownerDao;
    }

    public OwnerDto createOwner(String name, LocalDate birthDate) {
        Owner owner = new Owner(name, birthDate, new ArrayList<>());
        ownerDao.add(owner);
        return owner.asDto();
    }

    public void addKitty(int ownerId, int kittyId) {
        if (ownerDao.getById(ownerId) == null) {
            throw OwnerServiceException.noSuchOwner(ownerId);
        }
        if (kittyDao.getById(kittyId) != null) {
            throw OwnerServiceException.kittyAlreadyExistsException(kittyId);
        }
        Owner owner = ownerDao.getById(ownerId);
        Kitty kitty = kittyDao.getById(kittyId);
        owner.addKitty(kitty);
        kitty.setOwner(owner);
        ownerDao.update(owner);
        kittyDao.update(kitty);
    }

    public OwnerDto getOwnerById(int id) {
        if (ownerDao.getById(id) == null) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        return ownerDao.getById(id).asDto();
    }

    public List<KittyDto> findAllKitties(int id) {
        if (ownerDao.getById(id) == null) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : ownerDao.getAllKitties(id)) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<OwnerDto> findAllOwners() {
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerDao.getAll()) {
            owners.add(owner.asDto());
        }
        return owners;
    }

    public void removeOwner(int id) {
        if (ownerDao.getById(id) == null) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        ownerDao.remove(ownerDao.getById(id));
    }
}
