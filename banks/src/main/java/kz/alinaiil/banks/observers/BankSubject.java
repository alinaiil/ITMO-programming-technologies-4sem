package kz.alinaiil.banks.observers;

/**
 * Lists methods for BankSubjects
 * @author alinaiil
 * @version 1.0
 */
public interface BankSubject {
    /**
     * Attaches new observers
     * @param bankObserver Observer to be attached
     */
    void attach(BankObserver bankObserver);

    /**
     * Detaches observers
     * @param bankObserver Observers to be detached
     */
    void detach(BankObserver bankObserver);

    /**
     * Notifies observers
     * @param message Message to include in the notification
     */
    void notify(String message);
}
