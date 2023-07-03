package kz.alinaiil.banks.interfaces;

import kz.alinaiil.banks.clocks.Clock;
import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.models.CreditConditions;
import kz.alinaiil.banks.models.DebitInterest;
import kz.alinaiil.banks.models.DepositInterest;
import kz.alinaiil.banks.models.SuspiciousClientLimit;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Lists methods for the central bank
 * @author alinaiil
 * @version 1.0
 */
public interface CentralBank {
    /**
     * Adds bank
     * @param name Name of the bank
     * @param debitInterest Debit interest of the bank
     * @param depositInterests Deposit interest of the bank
     * @param creditConditions Credit conditions of the bank
     * @param suspiciousClientLimit Suspicious client limit of the bank
     * @return Returns created bank
     */
    Bank addBank(String name, DebitInterest debitInterest, List<DepositInterest> depositInterests, CreditConditions creditConditions, SuspiciousClientLimit suspiciousClientLimit);

    /**
     * Replenishes account
     * @param account Account to replenish
     * @param amount Amount of the replenishment
     * @return Returns id of the transaction
     */
    UUID replenishAccount(Account account, BigDecimal amount);

    /**
     * Withdraws money from account
     * @param account Account for the withdrawal
     * @param amount Amount of money to be withdrawn
     * @return Returns id of the transaction
     */
    UUID withdraw(Account account, BigDecimal amount);

    /**
     * Transfers money from one account to another
     * @param accountSender Account that sends money
     * @param accountReceiver Account that receives money
     * @param amount Amount of money to be transferred
     * @return Returns id of the transaction
     */
    UUID transfer(Account accountSender, Account accountReceiver, BigDecimal amount);

    /**
     * Cancels particular transaction
     * @param transactionId id of the transaction to cancel
     */
    void cancelTransaction(UUID transactionId);

    /**
     * Changes clock to a new one
     * @param newClock New clock for this central bank
     */
    void changeClock(Clock newClock);

    /**
     * Fast-forwards in time
     * @param days Amount of days to be added to the current date
     */
    void addDays(int days);

    /**
     * Returns particular bank by its name
     * @param name Name of the bank
     * @return Returns particular bank by its name
     */
    Bank getBank(String name);

    /**
     * Returns central bank's clock
     * @return Returns central bank's clock
     */
    Clock getClock();
}
