package kz.alinaiil.banks.interfaces;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.observers.ClientNotifier;

import java.util.List;
import java.util.UUID;

/**
 * Lists methods for the clients
 * @author alinaiil
 * @version 1.0
 */
public interface Client {
    /**
     * Checks if the client is suspicious
     * @return True - client is suspicious, False - client is not suspicious
     */
    boolean isSuspicious();

    /**
     * Changes client's notifier
     * @param clientNotifier New notifier for the client
     */
    void changeNotifier(ClientNotifier clientNotifier);

    /**
     * Adds account for client
     * @param account Account to add for client
     */
    void addAccount(Account account);

    /**
     * Adds client's address
     * @param address Client's address (or null)
     */
    void addAddress(String address);

    /**
     * Adds client's passport number
     * @param passport Client's passport number (or null)
     */
    void addPassport(Integer passport);

    /**
     * Returns client's id
     * @return Returns client's id
     */
    UUID getId();

    /**
     * Returns client's name
     * @return Returns client's name
     */
    String getName();

    /**
     * Returns client's surname
     * @return Returns client's surname
     */
    String getSurname();

    /**
     * Returns list with all the accounts of the client
     * @return Returns list with all the accounts of the client
     */
    List<Account> getAccounts();
}
