package kz.alinaiil.banks.exceptions;

/**
 * Exception class for the bank conditions
 * @author alinaiil
 * @version 1.0
 */
public class BankConditionsException extends BanksException{
    private BankConditionsException(String message) {
        super(message);
    }

    public static BankConditionsException depositInterestSwitchedBoundsException()
    {
        return new BankConditionsException("Incorrect bounds in deposit interest: lower bound mustn't be bigger than higher bound");
    }

    public static BankConditionsException depositInterestNegativeBoundsException()
    {
        return new BankConditionsException("Incorrect bounds in deposit interest: neither of bounds can be negative");
    }

    public static BankConditionsException depositInterestIncorrectInterestException()
    {
        return new BankConditionsException("Deposit interest must be bigger than 0% and smaller than 100%");
    }

    public static BankConditionsException debitInterestIncorrectInterestException()
    {
        return new BankConditionsException("Debit interest must be bigger than 0% and smaller than 100%");
    }

    public static BankConditionsException creditLimitIsNegativeException()
    {
        return new BankConditionsException("Credit limit value must be positive");
    }

    public static BankConditionsException creditFeeIsNegativeException()
    {
        return new BankConditionsException("Credit fee value must be positive");
    }

    public static BankConditionsException suspiciousClientLimitIsNegativeException()
    {
        return new BankConditionsException("Limit for suspicious client must not be negative");
    }
}
