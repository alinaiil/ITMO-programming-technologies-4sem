import kz.alinaiil.kotiki.data.models.Breed;
import kz.alinaiil.kotiki.data.models.Colour;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.models.Owner;
import kz.alinaiil.kotiki.data.repositories.KittyRepository;
import kz.alinaiil.kotiki.data.repositories.OwnerRepository;
import kz.alinaiil.kotiki.data.repositories.UserRepository;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.dto.OwnerDto;
import kz.alinaiil.kotiki.service.services.KittyServiceImpl;
import kz.alinaiil.kotiki.service.services.OwnerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {OwnerRepository.class, KittyRepository.class})
@ContextConfiguration
public class KittyServiceTest {
    @MockBean
    private KittyRepository kittyRepository;
    @MockBean
    private OwnerRepository ownerRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    void findAll_findsAll() {
        List<OwnerDto> ownersDto = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        Owner owner1 = new Owner("Alina", LocalDate.of(2003, 4, 16), new ArrayList<>());
        Owner owner2 = new Owner("Dina", LocalDate.of(1994, 7, 24), new ArrayList<>());
        owners.add(owner1);
        owners.add(owner2);
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerRepository, userRepository);
        Mockito.when(ownerRepository.save(Mockito.any(Owner.class))).thenReturn(null);
        ownersDto.add(ownerService.createOwner("Alina", LocalDate.of(2003, 4, 16)));
        ownersDto.add(ownerService.createOwner("Dina", LocalDate.of(1994, 7, 24)));
        Mockito.when(ownerRepository.findAll()).thenReturn(owners);
        Assertions.assertEquals(ownersDto, ownerService.findAllOwners());
    }

    @Test
    void findGingerKitties_gingerKittiesAreFound() {
        List<Kitty> kitties = new ArrayList<>();
        KittyServiceImpl kittyService = new KittyServiceImpl(kittyRepository, ownerRepository, userRepository);
        Owner owner1 = new Owner("Alina", LocalDate.of(2003, 4, 16), new ArrayList<>());
        Kitty kitty1 = new Kitty("Ryzhik", LocalDate.of(1999, 8, 21), Breed.Unknown, Colour.Ginger, owner1, new ArrayList<>());
        Kitty kitty2 = new Kitty("NeRyzhik", LocalDate.of(2002, 8, 21), Breed.Unknown, Colour.White, owner1, new ArrayList<>());
        Kitty kitty3 = new Kitty("AnotherRyzhik", LocalDate.of(2006, 8, 21), Breed.Unknown, Colour.Ginger, owner1, new ArrayList<>());
        kitties.add(kitty1);
        kitties.add(kitty2);
        kitties.add(kitty3);
        Mockito.when(ownerRepository.existsById(0)).thenReturn(true);
        Mockito.when(ownerRepository.save(Mockito.any(Owner.class))).thenReturn(null);
        Mockito.when(kittyRepository.save(Mockito.any(Kitty.class))).thenReturn(null);
        Mockito.when(ownerRepository.findById(owner1.getId())).thenReturn(Optional.of(owner1));
        Mockito.when(kittyRepository.findAll()).thenReturn(kitties);
        List<KittyDto> gingerKitties = new ArrayList<>();
        gingerKitties.add(kittyService.createKitty("Ryzhik", LocalDate.of(1999, 8, 21), "Unknown", "Ginger", owner1.getId()));
        gingerKitties.add(kittyService.createKitty("AnotherRyzhik", LocalDate.of(2006, 8, 21), "Unknown", "Ginger", owner1.getId()));
        Assertions.assertEquals(gingerKitties, kittyService.findKittiesByColour("Ginger"));
    }

    @Test
    void findPersianKitties_gingerKittiesAreFound() {
        List<Kitty> kitties = new ArrayList<>();
        KittyServiceImpl kittyService = new KittyServiceImpl(kittyRepository, ownerRepository, userRepository);
        Owner owner1 = new Owner("Alina", LocalDate.of(2003, 4, 16), new ArrayList<>());
        Kitty kitty1 = new Kitty("Ryzhik", LocalDate.of(1999, 8, 21), Breed.Unknown, Colour.Ginger, owner1, new ArrayList<>());
        Kitty kitty2 = new Kitty("NeRyzhik", LocalDate.of(2002, 8, 21), Breed.Persian, Colour.White, owner1, new ArrayList<>());
        Kitty kitty3 = new Kitty("AnotherRyzhik", LocalDate.of(2006, 8, 21), Breed.Persian, Colour.Ginger, owner1, new ArrayList<>());
        kitties.add(kitty1);
        kitties.add(kitty2);
        kitties.add(kitty3);
        Mockito.when(ownerRepository.existsById(0)).thenReturn(true);
        Mockito.when(ownerRepository.save(Mockito.any(Owner.class))).thenReturn(null);
        Mockito.when(kittyRepository.save(Mockito.any(Kitty.class))).thenReturn(null);
        Mockito.when(ownerRepository.findById(owner1.getId())).thenReturn(Optional.of(owner1));
        Mockito.when(kittyRepository.findAll()).thenReturn(kitties);
        List<KittyDto> persianKitties = new ArrayList<>();
        persianKitties.add(kittyService.createKitty("NeRyzhik", LocalDate.of(2002, 8, 21), "Persian", "White", owner1.getId()));
        persianKitties.add(kittyService.createKitty("AnotherRyzhik", LocalDate.of(2006, 8, 21), "Persian", "Ginger", owner1.getId()));
        Assertions.assertEquals(persianKitties, kittyService.findKittiesByBreed("Persian"));
    }
}
