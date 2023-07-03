package kz.alinaiil.kotiki.controller.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import kz.alinaiil.kotiki.controller.creators.KittyCreateDto;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.services.KittyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<KittyDto> getKittyById(@PathVariable int id) {
        if (!isAdmin()) {
            if (!kittyServiceImpl.isCurrentOwnersKitty(id)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(kittyServiceImpl.getKittyById(id), HttpStatus.OK);
    }

    @GetMapping("friends/{id}")
    public ResponseEntity<List<KittyDto>> findAllFriends(@PathVariable int id) {
        if (isAdmin()) {
            return new ResponseEntity<>(kittyServiceImpl.findAllFriends(id), HttpStatus.OK);
        } else {
            if (!kittyServiceImpl.isCurrentOwnersKitty(id)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(kittyServiceImpl.findAllFriendsSameOwner(id), HttpStatus.OK);
        }
    }

    @GetMapping()
    public List<KittyDto> findAllKitties() {
        if (isAdmin()) {
            return kittyServiceImpl.findAllKitties();
        } else {
            return kittyServiceImpl.findAllKittiesOfOwner();
        }
    }

    @DeleteMapping("{id}")
    public String removeKitty(@PathVariable int id) {
        kittyServiceImpl.removeKitty(id);
        return "Kitty with id " + id + " was deleted";
    }

    @GetMapping("get")
    public List<KittyDto> findKittyBy(@NotBlank(message = "Breed should not be blank") @RequestParam(name = "breed", defaultValue = "empty") String breed, @RequestParam(name = "colour", defaultValue = "empty") @NotBlank(message = "Colour should not be blank") String colour) {
        if (isAdmin()) {
            if (colour.equals("empty")) {
                return kittyServiceImpl.findKittiesByBreed(breed);
            } else if (breed.equals("empty")) {
                return kittyServiceImpl.findKittiesByColour(colour);
            } else {
                return kittyServiceImpl.findKittiesByColourAndBreed(colour, breed);
            }
        } else {
            if (colour.equals("empty")) {
                return kittyServiceImpl.findKittiesByBreedAndOwner(breed);
            } else if (breed.equals("empty")) {
                return kittyServiceImpl.findKittiesByColourAndOwner(colour);
            } else {
                return kittyServiceImpl.findKittiesByColourAndBreedAndOwner(colour, breed);
            }
        }
    }

    @PutMapping("befriend")
    public ResponseEntity<String> makeFriends(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "friend") int kittyId2) {
        boolean check = (!kittyServiceImpl.isCurrentOwnersKitty(kittyId1) || !kittyServiceImpl.isCurrentOwnersKitty(kittyId2));
        if (!isAdmin() && check) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        kittyServiceImpl.makeFriends(kittyId1, kittyId2);
        return new ResponseEntity<>("Kitties " + kittyId1 + " and " + kittyId2 + " are friends now!", HttpStatus.OK);
    }

    @PutMapping("unfriend")
    public ResponseEntity<String> unfriend(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "ex-friend") int kittyId2) {
        if (!isAdmin() && (!kittyServiceImpl.isCurrentOwnersKitty(kittyId1) || !kittyServiceImpl.isCurrentOwnersKitty(kittyId2))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        kittyServiceImpl.unfriendKitties(kittyId1, kittyId2);
        return new ResponseEntity<>("Kitties " + kittyId1 + " and " + kittyId2 + " are not friends", HttpStatus.OK);
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }
}
