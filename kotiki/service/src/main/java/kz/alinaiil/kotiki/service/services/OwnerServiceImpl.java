package kz.alinaiil.kotiki.service.services;

import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.models.Owner;
import kz.alinaiil.kotiki.data.repositories.OwnerRepository;
import kz.alinaiil.kotiki.data.repositories.UserRepository;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.dto.OwnerDto;
import kz.alinaiil.kotiki.service.exceptions.OwnerServiceException;
import kz.alinaiil.kotiki.service.mappers.KittyMapper;
import kz.alinaiil.kotiki.service.mappers.OwnerMapper;
import lombok.experimental.ExtensionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@ExtensionMethod({OwnerMapper.class, KittyMapper.class})
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, UserRepository userRepository) {
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
    }

    public OwnerDto createOwner(String name, LocalDate birthDate) {
        Owner owner = new Owner(name, birthDate, new ArrayList<>());
        ownerRepository.save(owner);
        return owner.asDto();
    }

    public OwnerDto getOwnerById(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        return ownerRepository.getReferenceById(id).asDto();
    }

    public List<KittyDto> findAllKitties(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        ownerRepository.getReferenceById(id);
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : ownerRepository.getReferenceById(id).getKitties()) {
            kitties.add(kitty.asDto());
        }
        return kitties;
    }

    public List<OwnerDto> findAllOwners() {
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerRepository.findAll()) {
            owners.add(owner.asDto());
        }
        return owners;
    }

    public void removeOwner(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        userRepository.deleteById(userRepository.findUserByOwner(ownerRepository.findById(id).orElseThrow()).orElseThrow().getId());
        ownerRepository.deleteById(id);
    }
}
