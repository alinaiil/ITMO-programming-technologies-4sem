package kz.alinaiil.kotiki.kitties.services;

import kz.alinaiil.kotiki.data.dto.FilterDto;
import kz.alinaiil.kotiki.data.dto.FriendsDto;
import kz.alinaiil.kotiki.data.dto.KittyCreateDto;
import kz.alinaiil.kotiki.data.dto.KittyDto;

import java.time.LocalDate;
import java.util.List;

public interface KittyService {
    KittyDto createKitty(KittyCreateDto kittyCreateDto);
    void makeFriends(FriendsDto friendsDto);
    void unfriendKitties(FriendsDto friendsDto);
    void getKittyById(int id);
    void findAllKitties(int trash);
    void findAllFriends(int id);
    void removeKitty(int id);
    void findKittiesByBreed(FilterDto filterDto);
    void findKittiesByColour(FilterDto filterDto);
    void findKittiesByColourAndBreed(FilterDto filterDto);
}
