package kz.alinaiil.banks.observers;

/**
 * Lists methods for ClientNotifiers
 * @author alinaiil
 * @version 1.0
 */
public interface ClientNotifier {
    /**
     * Notifies clients
     * @param message Message to send in the notification
     */
    void notify(String message);
}
