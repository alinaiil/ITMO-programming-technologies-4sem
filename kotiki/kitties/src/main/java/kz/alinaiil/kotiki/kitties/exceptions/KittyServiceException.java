package kz.alinaiil.kotiki.kitties.exceptions;

public class KittyServiceException extends KittiesException {
    private KittyServiceException(String message) {
        super(message);
    }

    public static KittyServiceException noSuchKitty(int id) {
        return new KittyServiceException("Kitty with id " + id + " doesn't exist");
    }

    public static KittyServiceException noSuchOwner(int ownerId) {
        return new KittyServiceException("Owner with id " + ownerId + " doesn't exist");
    }

    public static KittyServiceException noSuchColour(String colourName) {
        return new KittyServiceException("There is no colour " + colourName + " in owr database");
    }
}
