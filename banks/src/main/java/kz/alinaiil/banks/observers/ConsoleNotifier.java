package kz.alinaiil.banks.observers;

/**
 * Notifies clients via console
 * @author alinaiil
 * @version 1.0
 */
public class ConsoleNotifier implements ClientNotifier {
    /**
     * Notifies clients
     * @param message Message to send in the notification
     */
    @Override
    public void notify(String message) {
        System.out.println("Attention! New notification: " + message);
    }
}
