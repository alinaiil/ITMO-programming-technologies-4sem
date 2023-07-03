package kz.alinaiil.banks.exceptions;

/**
 * Exception class for the clocks
 * @author alinaiil
 * @version 1.0
 */
public class ClockException extends BanksException{
    private ClockException(String message) {
        super(message);
    }

    public static ClockException clockNegativeDaysException()
    {
        return new ClockException("Number of days must be positive");
    }
}
