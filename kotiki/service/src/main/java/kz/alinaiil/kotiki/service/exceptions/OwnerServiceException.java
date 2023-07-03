package kz.alinaiil.kotiki.service.exceptions;

public class OwnerServiceException extends KittiesException {
    private OwnerServiceException(String message) {
        super(message);
    }

    public static OwnerServiceException kittyAlreadyExistsException(int kittyId) {
        return new OwnerServiceException("Kitty with id " + kittyId + " already exists and is already taken");
    }

    public static OwnerServiceException noSuchOwner(int ownerId) {
        return new OwnerServiceException("Owner with id " + ownerId + " doesn't exist");
    }
}
