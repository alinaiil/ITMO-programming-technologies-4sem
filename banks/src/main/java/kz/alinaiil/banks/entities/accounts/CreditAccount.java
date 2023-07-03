package kz.alinaiil.banks.entities.accounts;

import kz.alinaiil.banks.clocks.Clock;
import kz.alinaiil.banks.exceptions.AccountException;
import kz.alinaiil.banks.exceptions.BanksException;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.Client;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Class for the credit account
 * @author alinaiil
 * @version 1.0
 */
@Getter
@Setter
public class CreditAccount implements Account {
    private String accountType = "Credit";

    /**
     * Creates credit account
     * @param client Client to whom this account belongs
     * @param bank Bank in which this account is opened
     * @param openingBalance Balance with which the account is opened
     * @param clock Clock for this account
     */
    public CreditAccount(Client client, Bank bank, BigDecimal openingBalance, Clock clock)
    {
        accountClient = client;
        accountBank = bank;
        balance = openingBalance;
        id = UUID.randomUUID();
    }

    /**
     * This account's id
     */
    public UUID id;
    /**
     * Client to whom this account belongs
     */
    public Client accountClient;
    /**
     * Bank in which this account is opened
     */
    public Bank accountBank;
    /**
     * Balance of this account
     */
    public BigDecimal balance;

    @Override
    public boolean isSuspicious() {
        return accountClient.isSuspicious();
    }

    @Override
    public BigDecimal fixedFee() {
        return accountBank.getCreditConditions().creditFee;
    }

    @Override
    public void withdrawMoney(BigDecimal amount) throws BanksException {
        checkAmount(amount);

        if (isSuspicious() && amount.compareTo(accountBank.getSuspiciousClientLimit().limit) > 0) {
            throw AccountException.suspiciousClientWithdrawException(amount, accountBank.getId());
        }

        if (balance.subtract(amount).compareTo(BigDecimal.valueOf(0)) < 0) {
            if (balance.subtract(amount).subtract(fixedFee()).compareTo(accountBank.getCreditConditions().creditLimit.multiply(BigDecimal.valueOf(-1))) < 0) {
                throw AccountException.creditAccountNotEnoughMoneyException(amount);
            }

            balance = balance.subtract(amount.add(fixedFee()));
        }
        else {
            balance = balance.subtract(amount.add(amount));
        }
    }

    @Override
    public void replenishAccount(BigDecimal amount) {
        checkAmount(amount);
        balance = balance.add(amount);
    }

    /**
     * Checks if amount is valid
     * @param amount Amount to check
     * @throws BanksException Throws exception if amount is invalid
     */
    private static void checkAmount(BigDecimal amount) throws BanksException {
        if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw AccountException.amountIsNegativeException();
        }
    }
}
