package kz.alinaiil.banks.clocks;

import kz.alinaiil.banks.observers.ClockObserver;

import java.util.Date;

/**
 * Lists methods all clocks should have
 * @author alinaiil
 * @version 1.0
 */
public interface Clock {
    /**
     * Fast-forwards time
     * @param days How many days to add
     */
    void addDays(int days);
    /**
     * Attaches observers
     * @param clockObserver Observer to be attached
     */
    void attach(ClockObserver clockObserver);
    /**
     * Notifies attached observers
     * @param currentTime Updated date and time that needs to be announced
     */
    void notify(Date currentTime);

    /**
     * Returns current time
     */
    Date getCurrentTime();
}
