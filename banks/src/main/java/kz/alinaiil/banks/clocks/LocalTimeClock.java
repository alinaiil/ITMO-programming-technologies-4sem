package kz.alinaiil.banks.clocks;

import kz.alinaiil.banks.exceptions.BanksException;
import kz.alinaiil.banks.exceptions.ClockException;
import kz.alinaiil.banks.observers.ClockObserver;
import lombok.Getter;

import java.util.*;

/**
 * Class that helps to work with virtual time in this lab:
 * to check current date and to fast-forward days
 *
 * @author alinaiil
 * @version 1.0
 */

@Getter
public class LocalTimeClock implements Clock {
    private final List<ClockObserver> observers;
    private final Timer timer;

    /**
     * Creates new clock and sets a timer
     */
    public LocalTimeClock() {
        currentTime = Calendar.getInstance().getTime();
        timer = new Timer();
        observers = new ArrayList<ClockObserver>();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                addDays(1);
            }
        };
        long delay = 1000L;
        long period = 1000L * 60L * 60L * 24L;
        timer.scheduleAtFixedRate(task, delay, period);
    }

    /**
     * Stores current time
     */
    public Date currentTime;

    @Override
    public void addDays(int days) throws BanksException {
        if (days < 0) {
            throw ClockException.clockNegativeDaysException();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DATE, days);
        currentTime = calendar.getTime();
        notify(currentTime);
    }

    @Override
    public void attach(ClockObserver clockObserver) {
        observers.add(clockObserver);
    }

    @Override
    public void notify(Date currentTime) {
        for (ClockObserver observer : observers) {
            observer.update(currentTime);
        }
    }
}
