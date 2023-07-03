package kz.alinaiil.banks.interfaces;

import kz.alinaiil.banks.clocks.Clock;
import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.models.CreditConditions;
import kz.alinaiil.banks.models.DebitInterest;
import kz.alinaiil.banks.models.DepositInterest;
import kz.alinaiil.banks.models.SuspiciousClientLimit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import java.util.UUID;

/**
 * Lists methods for the banks
 * @author alinaiil
 * @version 1.0
 */
public interface Bank {
    /**
     * Returns debit interest of this bank
     * @return Returns debit interest of this bank
     */
    DebitInterest getDebitInterest();

    /**
     * Returns credit conditions of this bank
     * @return Returns credit conditions of this bank
     */
    CreditConditions getCreditConditions();

    /**
     * Returns suspicious client limit of this bank
     * @return Returns suspicious client limit of this bank
     */
    SuspiciousClientLimit getSuspiciousClientLimit();

    /**
     * Returns deposit interest of this bank for particular opening balance
     * @param openingBalance Given opening balance to calculate the deposit interest
     * @return Returns deposit interest
     */
    DepositInterest getDepositInterest(BigDecimal openingBalance);

    /**
     * Adds client to this bank
     * @param client Client to add
     */
    void addClient(Client client);

    /**
     * Creates debit account in this bank
     * @param client Client for whom banks creates an account
     * @param openingBalance Opening balance of the account
     * @return Returns created account
     */
    Account createDebitAccount(Client client, BigDecimal openingBalance);

    /**
     * Creates credit account in this bank
     * @param client Client for whom banks creates an account
     * @param openingBalance Opening balance of the account
     * @return Returns created account
     */
    Account createCreditAccount(Client client, BigDecimal openingBalance);

    /**
     * Creates deposit account in this bank
     * @param client Client for whom banks creates an account
     * @param openingBalance Opening balance of the account
     * @param dueTo Deposit is unreachable due this date
     * @return Returns created account
     */
    Account createDepositAccount(Client client, BigDecimal openingBalance, Date dueTo);

    /**
     * Changes debit interest conditions
     * @param newInterest New interest for this bank
     */
    void changeDebitInterest(BigDecimal newInterest);

    /**
     * Changes deposit interests conditions
     * @param newDepositInterests New interests for this bank
     */
    void changeDepositInterests(List<DepositInterest> newDepositInterests);

    /**
     * Changes credit limit conditions
     * @param newCreditLimit New credit limit for this bank
     */
    void changeCreditLimit(BigDecimal newCreditLimit);

    /**
     * Changes credit fee conditions
     * @param newCreditFee New credit fee for this bank
     */
    void changeCreditFee(BigDecimal newCreditFee);

    /**
     * Changes suspicious client limit conditions
     * @param newLimit New suspicious client limit for this bank
     */
    void changeSuspiciousClientLimit(BigDecimal newLimit);

    /**
     * Changes clock to a new one
     * @param newClock New clock for this bank
     */
    void changeClock(Clock newClock);

    /**
     * Gets particular client of this bank by name and surname
     * @param name Name of the client
     * @param surname Surname of the client
     * @return Returns particular client
     */
    Client getClient(String name, String surname);

    /**
     * Gets particular account in this bank by id
     * @param id id of the account
     * @return Returns particular account
     */
    Account getAccount(String id);

    /**
     * Returns id of this bank
     * @return Returns id of this bank
     */
    UUID getId();

    /**
     * Returns the name of this bank
     * @return Returns the name of this bank
     */
    String getName();

    /**
     * Returns list of all the bank's accounts
     * @return Returns list of all the bank's accounts
     */
    List<Account> getAccounts();
}
