package kz.alinaiil.banks.exceptions;

/**
 * Main exception class for the Banks project
 * @author alinaiil
 * @version 1.0
 */
public class BanksException extends RuntimeException {
    public BanksException(String message) {
        super(message);
    }
}
