package kz.alinaiil.kotiki.service.exceptions;

public class UserServiceException extends RuntimeException {
    private UserServiceException(String message) {
        super(message);
    }

    public static UserServiceException noSuchOwner(int ownerId) {
        return new UserServiceException("Owner with id " + ownerId + " doesn't exist");
    }

    public static UserServiceException noSuchRole(String roleName) {
        return new UserServiceException("Role " + roleName+ " doesn't exist");
    }

    public static UserServiceException usernameIsTaken(String username) {
        return new UserServiceException("User with username \"" + username + "\" already exists");
    }

    public static UserServiceException ownerAlreadyRegistered(int ownerId) {
        return new UserServiceException("User for owner with id " + ownerId + " is already created");
    }
}
