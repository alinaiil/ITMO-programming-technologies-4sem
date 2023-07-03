package kz.alinaiil.banks.entities.banks;

import kz.alinaiil.banks.clocks.Clock;
import kz.alinaiil.banks.clocks.LocalTimeClock;
import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.exceptions.BanksException;
import kz.alinaiil.banks.exceptions.CentralBankException;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.CentralBank;
import kz.alinaiil.banks.models.CreditConditions;
import kz.alinaiil.banks.models.DebitInterest;
import kz.alinaiil.banks.models.DepositInterest;
import kz.alinaiil.banks.models.SuspiciousClientLimit;
import kz.alinaiil.banks.transactions.Replenish;
import kz.alinaiil.banks.transactions.TransactionInvoker;
import kz.alinaiil.banks.transactions.Transfer;
import kz.alinaiil.banks.transactions.Withdraw;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class for the concrete central bank
 * @author alinaiil
 * @version 1.0
 */
@Getter
public class ConcreteCentralBank implements CentralBank {
    private final List<Bank> banks;
    private final TransactionInvoker transactionInvoker;
    private Clock clock = new LocalTimeClock();

    /**
     * Creates concrete central bank
     */
    public ConcreteCentralBank() {
        banks = new ArrayList<Bank>();
        transactionInvoker = new TransactionInvoker();
    }

    @Override
    public Bank addBank(String name, DebitInterest debitInterest, List<DepositInterest> depositInterests, CreditConditions creditConditions, SuspiciousClientLimit suspiciousClientLimit) throws BanksException {
        if (checkBank(name)) {
            throw CentralBankException.bankAlreadyExists(name);
        }

        Bank bank = new ConcreteBank(name, debitInterest, depositInterests, creditConditions, suspiciousClientLimit, clock);
        banks.add(bank);
        return bank;
    }

    @Override
    public UUID replenishAccount(Account account, BigDecimal amount) {
        UUID transactionId = transactionInvoker.setTransaction(new Replenish(account, amount));
        transactionInvoker.executeTransaction();
        return transactionId;
    }

    @Override
    public UUID withdraw(Account account, BigDecimal amount) {
        UUID transactionId = transactionInvoker.setTransaction(new Withdraw(account, amount));
        transactionInvoker.executeTransaction();
        return transactionId;
    }

    @Override
    public UUID transfer(Account accountSender, Account accountReceiver, BigDecimal amount) {
        UUID transactionId = transactionInvoker.setTransaction(new Transfer(accountSender, accountReceiver, amount));
        transactionInvoker.executeTransaction();
        return transactionId;
    }

    @Override
    public void cancelTransaction(UUID transactionId) {
        transactionInvoker.cancelTransaction(transactionId);
    }

    @Override
    public void changeClock(Clock newClock) {
        clock = newClock;
        banks.forEach(bank -> bank.changeClock(newClock));
    }

    @Override
    public void addDays(int days) {
        clock.addDays(days);
    }

    @Override
    public Bank getBank(String name) throws BanksException {
        if (!checkBank(name)) {
            throw CentralBankException.noSuchBankException(name);
        }

        for (Bank bank : banks) {
            if (bank.getName().equals(name)) {
                return bank;
            }
        }

        throw CentralBankException.noSuchBankException(name);
    }

    /**
     * Checks if the bank is already created by its name
     * @param name Name of the bank to check
     * @return True - if it is already created, false - if it is not created yet
     */
    private boolean checkBank(String name)
    {
        for (Bank bank : banks) {
            if (bank.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
