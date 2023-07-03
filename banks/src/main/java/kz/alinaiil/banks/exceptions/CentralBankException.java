package kz.alinaiil.banks.exceptions;

/**
 * Exception class for the central bank
 * @author alinaiil
 * @version 1.0
 */
public class CentralBankException extends BanksException {
    private CentralBankException(String message) {
        super(message);
    }

    public static CentralBankException noSuchBankException(String name)
    {
        return new CentralBankException("There is no bank with name " + name + " in the central bank");
    }

    public static CentralBankException bankAlreadyExists(String name)
    {
        return new CentralBankException("Bank with name " + name + " already exists");
    }
}
