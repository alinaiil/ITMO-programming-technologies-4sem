package kz.alinaiil.banks.exceptions;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Exception class for the account
 * @author alinaiil
 * @version 1.0
 */
public class AccountException extends BanksException {
    private AccountException(String message) {
        super(message);
    }

    public static AccountException amountIsNegativeException()
    {
        return new AccountException("Amount cannot be negative");
    }

    public static AccountException debitAccountNotEnoughMoneyException(BigDecimal balance, BigDecimal amount)
    {
        return new AccountException("Not enough money on a debit account. Current balance: " + balance + ", needed: " + amount);
    }

    public static AccountException suspiciousClientWithdrawException(BigDecimal amount, UUID id)
    {
        return new AccountException("Suspicious client cannot withdraw " + amount + " in the bank with id " + id);
    }

    public static AccountException depositAccountNotEnoughMoneyException(BigDecimal balance, BigDecimal amount)
    {
        return new AccountException("Not enough money on a deposit account. Current balance: " + balance + ", needed: " + amount);
    }

    public static AccountException depositTermHasNotEndedYetException()
    {
        return new AccountException("Money cannot be withdrawn from the deposit account for its term is not over yet");
    }

    public static AccountException creditAccountNotEnoughMoneyException(BigDecimal amount)
    {
        return new AccountException("Cannot withdraw " + amount + " due to set credit limit");
    }
}
