package kz.alinaiil.kotiki.owners.services;

import kz.alinaiil.kotiki.data.dto.*;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.models.Owner;
import kz.alinaiil.kotiki.data.repositories.OwnerRepository;
import kz.alinaiil.kotiki.owners.exceptions.OwnerServiceException;
import kz.alinaiil.kotiki.owners.mappers.KittyMapper;
import kz.alinaiil.kotiki.owners.mappers.OwnerMapper;
import lombok.experimental.ExtensionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ExtensionMethod({OwnerMapper.class, KittyMapper.class})
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final KafkaTemplate<String, OwnerDto> getOwner;
    private final KafkaTemplate<String, OwnerListDto> getOwners;
    private final KafkaTemplate<String, KittyListDto> getKitties;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, KafkaTemplate<String, OwnerDto> getOwner, KafkaTemplate<String, OwnerListDto> getOwners, KafkaTemplate<String, KittyListDto> getKitties) {
        this.ownerRepository = ownerRepository;
        this.getOwner = getOwner;
        this.getOwners = getOwners;
        this.getKitties = getKitties;
    }

    @KafkaListener(topics = "create_owner", groupId = "groupIdCO", containerFactory = "createOwnerFactory")
    public OwnerDto createOwner(OwnerCreateDto ownerCreateDto) {
        Owner owner = new Owner(ownerCreateDto.name(), ownerCreateDto.birthDate(), new ArrayList<>());
        ownerRepository.save(owner);
        return owner.asDto();
    }

    @KafkaListener(topics = "get_by_id_owner", groupId = "groupIdGBIDO", containerFactory = "byIdOwnerFactory")
    public void getOwnerById(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        getOwner.send("got_by_id_owner", ownerRepository.findById(id).orElseThrow().asDto());
    }

    @KafkaListener(topics = "get_by_id_owners_kitties", groupId = "groupIdGBIDOK", containerFactory = "byIdOwnerFactory")
    public void findAllKitties(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        ownerRepository.findById(id).orElseThrow();
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : ownerRepository.findById(id).orElseThrow().getKitties()) {
            kitties.add(kitty.asDto());
        }
        getKitties.send("got_by_id_owners_kitties", new KittyListDto(kitties));
    }

    @KafkaListener(topics = "get_owners", groupId = "groupIdGO", containerFactory = "byIdOwnerFactory")
    public void findAllOwners(int trash) {
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerRepository.findAll()) {
            owners.add(owner.asDto());
        }
        getOwners.send("got_owners", new OwnerListDto(owners));
    }

    @KafkaListener(topics = "remove_owner", groupId = "groupIdRO", containerFactory = "byIdOwnerFactory")
    public void removeOwner(int id) {
        if (!ownerRepository.existsById(id)) {
            throw OwnerServiceException.noSuchOwner(id);
        }
        ownerRepository.deleteById(id);
    }
}