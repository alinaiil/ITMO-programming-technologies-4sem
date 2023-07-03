package kz.alinaiil.kotiki.controller.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import kz.alinaiil.kotiki.controller.creators.KittyCreateDto;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.services.KittyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitties/")
public class KittyController {
    private final KittyServiceImpl kittyServiceImpl;

    @Autowired
    public KittyController(KittyServiceImpl kittyServiceImpl) {
        this.kittyServiceImpl = kittyServiceImpl;
    }

    @PostMapping()
    public KittyDto createKitty(@Valid @RequestBody KittyCreateDto kittyDto) {
        return kittyServiceImpl.createKitty(kittyDto.name(), kittyDto.birthDate(), kittyDto.breed(), kittyDto.colour(), kittyDto.ownerId());
    }

    @GetMapping("{id}")
    public KittyDto getKittyById(@PathVariable int id) {
        return kittyServiceImpl.getKittyById(id);
    }

    @GetMapping("friends/{id}")
    public List<KittyDto> findAllFriends(@PathVariable int id) {
        return kittyServiceImpl.findAllFriends(id);
    }

    @GetMapping()
    public List<KittyDto> findAllKitties() {
        return kittyServiceImpl.findAllKitties();
    }

    @DeleteMapping("{id}")
    public String removeKitty(@PathVariable int id) {
        kittyServiceImpl.removeKitty(id);
        return "Kitty with id " + id + " was deleted";
    }

    @GetMapping("get")
    public List<KittyDto> findKittyByBreed(@NotBlank(message = "Breed should not be blank") @RequestParam(name = "breed") String breed, @RequestParam(name = "colour", defaultValue = "empty") @NotBlank(message = "Colour should not be blank") String colour) {
        if (breed.equals("empty")) {
            return kittyServiceImpl.findKittiesByBreed(breed);
        } else if (colour.equals("empty")) {
            return kittyServiceImpl.findKittiesByColour(colour);
        } else {
            return kittyServiceImpl.findKittiesByColourAndBreed(colour, breed);
        }
    }

    @PutMapping("befriend")
    public String makeFriends(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "friend") int kittyId2) {
        kittyServiceImpl.makeFriends(kittyId1, kittyId2);
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are friends now!";
    }

    @PutMapping("unfriend")
    public String unfriend(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "ex-friend") int kittyId2) {
        kittyServiceImpl.unfriendKitties(kittyId1, kittyId2);
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are not friends";
    }
}
