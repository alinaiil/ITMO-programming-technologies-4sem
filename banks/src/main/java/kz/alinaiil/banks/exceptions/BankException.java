package kz.alinaiil.banks.exceptions;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Exception class for the banks
 * @author alinaiil
 * @version 1.0
 */
public class BankException extends BanksException {
    private BankException(String message) {
        super(message);
    }

    public static BankException noSuchObserverException(UUID id)
    {
        return new BankException("Cannot unsubscribe client with id " + id + ", for they are not in the list of subscribers");
    }

    public static BankException observerIsAlreadySubscribed(UUID id)
    {
        return new BankException("Client with id " + id + " is already subscribed");
    }

    public static BankException clientIsAlreadyRegistered(UUID clientId, UUID bankId) {
        return new BankException("Client with id " + clientId + " is already registered in bank with id " + bankId);
    }

    public static BankException accountIsAlreadyRegistered(UUID accountId, UUID bankId)
    {
        return new BankException("Account with id " + accountId + " is already registered in bank with id " + bankId);
    }

    public static BankException noMatchingDepositInterestException(BigDecimal openingBalance, UUID bankId)
    {
        return new BankException("Bank with id " + bankId + " doesn't have matching interest for a deposit account with opening balance " + openingBalance);
    }

    public static BankException noSuchClientException(String name, String surname)
    {
        return new BankException("There is no client called " + name + " " + surname);
    }

    public static BankException noSuchAccountException()
    {
        return new BankException("There is no such account");
    }
}
