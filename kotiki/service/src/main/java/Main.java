import kz.alinaiil.kotiki.data.dao.KittyDao;
import kz.alinaiil.kotiki.data.dao.OwnerDao;
import kz.alinaiil.kotiki.data.utils.SessionFactoryInstance;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.dto.OwnerDto;
import kz.alinaiil.kotiki.service.services.KittyServiceImpl;
import kz.alinaiil.kotiki.service.services.OwnerServiceImpl;
import org.hibernate.Session;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        KittyServiceImpl kittyServiceImpl = new KittyServiceImpl(new KittyDao(), new OwnerDao());
        OwnerServiceImpl ownerServiceImpl = new OwnerServiceImpl(new KittyDao(), new OwnerDao());

        OwnerDto owner = ownerServiceImpl.createOwner("Owner", LocalDate.of(2003, 4, 16));
        KittyDto kitty1 = kittyServiceImpl.createKitty("Kitty", LocalDate.of(2022, 12,1), "Munchkin", "White", 1);
        KittyDto kitty2 = kittyServiceImpl.createKitty("Kitty", LocalDate.of(2022, 12,1), "Munchkin", "White", 1);
        kittyServiceImpl.makeFriends(kitty1.getId(), kitty2.getId());
        kittyServiceImpl.unfriendKitties(kitty1.getId(), kitty2.getId());
        kittyServiceImpl.removeKitty(kitty1.getId());
        ownerServiceImpl.removeOwner(owner.getId());
        session.close();
    }
}
