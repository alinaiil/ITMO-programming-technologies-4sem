package kz.alinaiil.banks.entities.accounts;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Lists methods for accounts to have
 * @author alinaiil
 * @version 1.0
 */
public interface Account {
    /**
     * Checks if account's owner is suspicious
     * @return True - if account belongs to a suspicious client, False - if not
     */
    boolean isSuspicious();

    /**
     * Returns account's fixed fee
     * @return Fixed fee of an account
     */
    BigDecimal fixedFee();

    /**
     * Withdraws money from an account
     * @param amount Amount of money to withdraw
     */
    void withdrawMoney(BigDecimal amount);

    /**
     * Replenishes account
     * @param amount Amount of money for the replenishment
     */
    void replenishAccount(BigDecimal amount);

    /**
     * Gets account's id
     * @return Account id
     */
    UUID getId();

    /**
     * Returns account's current balance
     * @return Balance of the account
     */
    BigDecimal getBalance();

    /**
     * Returns name of the account's type
     * @return String with the name of account's type (e.g. Debit, Credit...)
     */
    String getAccountType();
}
