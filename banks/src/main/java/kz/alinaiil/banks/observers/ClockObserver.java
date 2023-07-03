package kz.alinaiil.banks.observers;

import java.util.Date;

/**
 * Lists methods for ClockObservers
 * @author alinaiil
 * @version 1.0
 */
public interface ClockObserver {
    /**
     * Gets clock notification about time update and works with it
     * @param currentTime Updated time
     */
    void update(Date currentTime);
}
