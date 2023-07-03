package kz.alinaiil.kotiki.kitties.services;

import kz.alinaiil.kotiki.data.dto.*;
import kz.alinaiil.kotiki.data.models.Breed;
import kz.alinaiil.kotiki.data.models.Colour;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.repositories.KittyRepository;
import kz.alinaiil.kotiki.data.repositories.OwnerRepository;
import kz.alinaiil.kotiki.kitties.exceptions.KittyServiceException;
import kz.alinaiil.kotiki.kitties.mappers.KittyMapper;
import lombok.experimental.ExtensionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ExtensionMethod(KittyMapper.class)
public class KittyServiceImpl implements KittyService {
    private final KittyRepository kittyRepository;
    private final OwnerRepository ownerRepository;
    private final KafkaTemplate<String, KittyDto> getKitty;
    private final KafkaTemplate<String, KittyListDto> getKitties;

    @Autowired
    public KittyServiceImpl(KittyRepository kittyRepository, OwnerRepository ownerRepository, KafkaTemplate<String, KittyDto> getKitty, KafkaTemplate<String, KittyListDto> getKitties) {
        this.kittyRepository = kittyRepository;
        this.ownerRepository = ownerRepository;
        this.getKitty = getKitty;
        this.getKitties = getKitties;
    }

    @KafkaListener(topics = "create_kitty", groupId = "groupIdCK", containerFactory = "createKittyFactory")
    public KittyDto createKitty(KittyCreateDto kittyCreateDto) {
        Breed breed = findBreed(kittyCreateDto.breed());
        Colour colour = findColour(kittyCreateDto.colour());
        if (!ownerRepository.existsById(kittyCreateDto.ownerId())) {
            throw KittyServiceException.noSuchOwner(kittyCreateDto.ownerId());
        }
        Kitty kitty = new Kitty(kittyCreateDto.name(), kittyCreateDto.birthDate(), breed, colour, ownerRepository.findById(kittyCreateDto.ownerId()).orElseThrow(), new ArrayList<>());
        ownerRepository.findById(kittyCreateDto.ownerId()).ifPresent(owner -> owner.addKitty(kitty));
        kittyRepository.save(kitty);
        return kitty.asDto();
    }

    @KafkaListener(topics = "befriend", groupId = "groupIdBefriend", containerFactory = "friendFactory")
    public void makeFriends(FriendsDto friendsDto) {
        if (!kittyRepository.existsById(friendsDto.friend1())) {
            throw KittyServiceException.noSuchKitty(friendsDto.friend1());
        }
        if (!kittyRepository.existsById(friendsDto.friend2())) {
            throw KittyServiceException.noSuchKitty(friendsDto.friend2());
        }

        Kitty kitty1 = kittyRepository.findById(friendsDto.friend1()).orElseThrow();
        Kitty kitty2 = kittyRepository.findById(friendsDto.friend2()).orElseThrow();
        kitty1.addFriend(kitty2);
        kitty2.addFriend(kitty1);
        kittyRepository.save(kitty1);
    }

    @KafkaListener(topics = "unfriend", groupId = "groupIdUnfriend", containerFactory = "friendFactory")
    public void unfriendKitties(FriendsDto friendsDto) {
        if (!kittyRepository.existsById(friendsDto.friend1())) {
            throw KittyServiceException.noSuchKitty(friendsDto.friend1());
        }
        if (!kittyRepository.existsById(friendsDto.friend2())) {
            throw KittyServiceException.noSuchKitty(friendsDto.friend2());
        }
        Kitty kitty1 = kittyRepository.findById(friendsDto.friend1()).orElseThrow();
        Kitty kitty2 = kittyRepository.findById(friendsDto.friend2()).orElseThrow();
        kitty1.unfriend(kitty2);
        kittyRepository.save(kitty1);
        kitty2.unfriend(kitty1);
        kittyRepository.save(kitty2);
    }

    @KafkaListener(topics = "get_by_id_kitty", groupId = "groupIdGBIDK", containerFactory = "byIdKittyFactory")
    public void getKittyById(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyServiceException.noSuchKitty(id);
        }
        getKitty.send("got_by_id_kitty", kittyRepository.findById(id).orElseThrow().asDto());
    }

    @KafkaListener(topics = "get_kitties", groupId = "groupIdGBIDKs", containerFactory = "byIdKittyFactory")
    public void findAllKitties(int trash) {
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            kitties.add(kitty.asDto());
        }
        getKitties.send("got_kitties", new KittyListDto(kitties));
    }

    @KafkaListener(topics = "get_by_id_friends", groupId = "groupIdGBIDKF", containerFactory = "byIdKittyFactory")
    public void findAllFriends(int id) {
        if (!kittyRepository.existsById(id)) {
            throw KittyServiceException.noSuchKitty(id);
        }
        List<KittyDto> friends = new ArrayList<>();
        for (Kitty friend : kittyRepository.findById(id).orElseThrow().getFriends()) {
            friends.add(friend.asDto());
        }
        getKitties.send("got_by_id_friends", new KittyListDto(friends));
    }

    @KafkaListener(topics = "remove_kitty", groupId = "groupIdRK", containerFactory = "byIdKittyFactory")
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

    @KafkaListener(topics = "get_kitties_by_breed", groupId = "groupByBreed", containerFactory = "filterFactory")
    public void findKittiesByBreed(FilterDto filterDto) {
        Breed breed = Breed.valueOf(filterDto.breed());
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getBreed().equals(breed)) {
                kitties.add(kitty.asDto());
            }
        }
        getKitties.send("got_kitties_by_filters", new KittyListDto(kitties));
    }

    @KafkaListener(topics = "get_kitties_by_colour", groupId = "groupByColour", containerFactory = "filterFactory")
    public void findKittiesByColour(FilterDto filterDto) {
        Colour colour = Colour.valueOf(filterDto.colour());
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getColour().equals(colour)) {
                kitties.add(kitty.asDto());
            }
        }
        getKitties.send("got_kitties_by_filters", new KittyListDto(kitties));
    }

    @KafkaListener(topics = "get_kitties_by_filters", groupId = "groupByColourAndBreed", containerFactory = "filterFactory")
    public void findKittiesByColourAndBreed(FilterDto filterDto) {
        Colour colour = Colour.valueOf(filterDto.colour());
        Breed breed = Breed.valueOf(filterDto.breed());
        List<KittyDto> kitties = new ArrayList<>();
        for (Kitty kitty : kittyRepository.findAll()) {
            if (kitty.getColour().equals(colour) && kitty.getBreed().equals(breed)) {
                kitties.add(kitty.asDto());
            }
        }
        getKitties.send("got_kitties_by_filters", new KittyListDto(kitties));
    }

    private Colour findColour(String colourName) {
        Colour colour;
        for (int i = 0; i < Colour.values().length; i++) {
            if (Colour.values()[i].name().equals(colourName)) {
                colour = Colour.valueOf(colourName);
                return colour;
            }
        }
        throw KittyServiceException.noSuchColour(colourName);
    }

    private Breed findBreed(String breedName) {
        Breed breed = Breed.Unknown;
        for (int i = 0; i < Breed.values().length; i++) {
            if (Breed.values()[i].name().equals(breedName)) {
                breed = Breed.valueOf(breedName);
                break;
            }
        }
        return breed;
    }
}