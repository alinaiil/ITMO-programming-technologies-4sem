package kz.alinaiil.banks.observers;

import java.util.UUID;

/**
 * Lists methods for BankObservers
 * @author alinaiil
 * @version 1.0
 */
public interface BankObserver {
    /**
     * Gets bank notification and works with it
     * @param message Message from the notification
     */
    void update(String message);

    /**
     * Returns observer's id
     * @return Observer's id
     */
    UUID getId();
}