import kz.alinaiil.kotiki.data.dao.KittyDao;
import kz.alinaiil.kotiki.data.dao.OwnerDao;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.dto.OwnerDto;
import kz.alinaiil.kotiki.service.exceptions.KittyServiceException;
import kz.alinaiil.kotiki.service.exceptions.OwnerServiceException;
import kz.alinaiil.kotiki.service.services.KittyService;
import kz.alinaiil.kotiki.service.services.KittyServiceImpl;
import kz.alinaiil.kotiki.service.services.OwnerService;
import kz.alinaiil.kotiki.service.services.OwnerServiceImpl;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KittiesTest {
    private Session session;
    private KittyService kittyServiceImpl;
    private OwnerService ownerServiceImpl;

    @BeforeEach
    void before() {
        kittyServiceImpl = new KittyServiceImpl(new KittyDao(), new OwnerDao());
        ownerServiceImpl = new OwnerServiceImpl(new KittyDao(), new OwnerDao());
    }

    @Test
    void createOwnerAddKitty_ownerAndKittyAreConnected() {
        OwnerDto owner = ownerServiceImpl.createOwner("Alina", LocalDate.of(2003, 4, 16));
        KittyDto kitty1 = kittyServiceImpl.createKitty("Kot", LocalDate.of(2022, 12,1), "Munchkin", "White", owner.getId());
        Assertions.assertEquals(owner.getId(), kitty1.getOwnerId());
    }

    @Test
    void addFriendsToKitty_allFriendsAreAdded() {
        OwnerDto owner = ownerServiceImpl.createOwner("Alina", LocalDate.of(2003, 4, 16));
        KittyDto kitty1 = kittyServiceImpl.createKitty("Kot", LocalDate.of(2022, 12,1), "Munchkin", "White", owner.getId());
        KittyDto kitty2 = kittyServiceImpl.createKitty("toK", LocalDate.of(2022, 12,1), "Munchkin", "White", owner.getId());
        KittyDto kitty3 = kittyServiceImpl.createKitty("KoT", LocalDate.of(2022, 12,1), "Munchkin", "White", owner.getId());
        kittyServiceImpl.makeFriends(kitty3.getId(), kitty2.getId());
        kittyServiceImpl.makeFriends(kitty1.getId(), kitty2.getId());
        List<Integer> friends = Arrays.asList(kitty3.getId(), kitty1.getId());
        List<Integer> check = new ArrayList<>();
        for (KittyDto kitty : kittyServiceImpl.findAllFriends(kitty2.getId())) {
            check.add(kitty.getId());
        }
        Assertions.assertEquals(friends, check);
    }

    @Test
    void removeOwner() {
        OwnerDto owner = ownerServiceImpl.createOwner("Alina", LocalDate.of(2003, 4, 16));
        KittyDto kitty1 = kittyServiceImpl.createKitty("Kot", LocalDate.of(2022, 12,1), "Munchkin", "White", owner.getId());
        int ownerId = owner.getId();
        int kittyId = kitty1.getId();
        ownerServiceImpl.removeOwner(owner.getId());
        Assertions.assertThrows(OwnerServiceException.class, () -> ownerServiceImpl.getOwnerById(ownerId));
        Assertions.assertThrows(KittyServiceException.class, () -> kittyServiceImpl.getKittyById(kittyId));
    }
}
