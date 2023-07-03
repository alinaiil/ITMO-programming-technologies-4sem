package kz.alinaiil.kotiki.owners.exceptions;

public class OwnerServiceException extends OwnersException {
    private OwnerServiceException(String message) {
        super(message);
    }

    public static OwnerServiceException noSuchOwner(int ownerId) {
        return new OwnerServiceException("Owner with id " + ownerId + " doesn't exist");
    }
}
