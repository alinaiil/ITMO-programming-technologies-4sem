package kz.alinaiil.banks.entities.banks;

import kz.alinaiil.banks.clocks.Clock;
import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.entities.accounts.CreditAccount;
import kz.alinaiil.banks.entities.accounts.DebitAccount;
import kz.alinaiil.banks.entities.accounts.DepositAccount;
import kz.alinaiil.banks.exceptions.BankException;
import kz.alinaiil.banks.exceptions.BanksException;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.Client;
import kz.alinaiil.banks.models.CreditConditions;
import kz.alinaiil.banks.models.DebitInterest;
import kz.alinaiil.banks.models.DepositInterest;
import kz.alinaiil.banks.models.SuspiciousClientLimit;
import kz.alinaiil.banks.observers.BankObserver;
import kz.alinaiil.banks.observers.BankSubject;
import kz.alinaiil.banks.transactions.TransactionInvoker;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

/**
 * Class for the concrete bank
 * @author alinaiil
 * @version 1.0
 */
@Getter
@Setter
public class ConcreteBank implements Bank, BankSubject {
    private final List<Client> clients;
    private final List<Account> accounts;
    private final List<BankObserver> observers;
    private Clock clock;
    private DebitInterest debitInterest;
    private CreditConditions creditConditions;
    private SuspiciousClientLimit suspiciousClientLimit;
    private List<DepositInterest> depositInterests;

    /**
     * Creates concrete bank
     * @param name Name of the bank
     * @param debitInterest Debit interest of the bank
     * @param depositInterests Deposit interest of the bank
     * @param creditConditions Credit conditions of the bank
     * @param suspiciousClientLimit Suspicious client limit of the bank
     * @param clock Clock for the bank
     */
    public ConcreteBank(String name, DebitInterest debitInterest, List<DepositInterest> depositInterests, CreditConditions creditConditions, SuspiciousClientLimit suspiciousClientLimit, Clock clock) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(clock);
        Objects.requireNonNull(debitInterest);
        Objects.requireNonNull(depositInterests);
        Objects.requireNonNull(creditConditions);
        Objects.requireNonNull(suspiciousClientLimit);
        clients = new ArrayList<Client>();
        accounts = new ArrayList<Account>();
        observers = new ArrayList<BankObserver>();
        this.name = name;
        id = UUID.randomUUID();
        this.clock = clock;
        this.debitInterest = debitInterest;
        this.depositInterests = depositInterests;
        this.creditConditions = creditConditions;
        this.suspiciousClientLimit = suspiciousClientLimit;
    }

    /**
     * Name of the bank
     */
    public String name;
    /**
     * id of the bank
     */
    public UUID id;

    @Override
    public DebitInterest getDebitInterest() {
        return debitInterest;
    }

    @Override
    public CreditConditions getCreditConditions() {
        return creditConditions;
    }

    @Override
    public SuspiciousClientLimit getSuspiciousClientLimit() {
        return suspiciousClientLimit;
    }

    @Override
    public DepositInterest getDepositInterest(BigDecimal openingBalance) throws BanksException {
        DepositInterest depositInterest = null;
        for (DepositInterest interest : depositInterests) {
            if (interest.isInRange(openingBalance)) {
                depositInterest = interest;
                break;
            }
        }

        if (depositInterest == null) {
            throw BankException.noMatchingDepositInterestException(openingBalance, this.id);
        }

        return depositInterest;
    }

    @Override
    public void addClient(Client client) throws BanksException {
        if (clients.contains(client)) {
            throw BankException.clientIsAlreadyRegistered(client.getId(), this.id);
        }

        clients.add(client);
    }

    @Override
    public Account createDebitAccount(Client client, BigDecimal openingBalance) {
        Account newDebitAccount = new DebitAccount(client, this, openingBalance, clock);
        client.addAccount(newDebitAccount);
        accounts.add(newDebitAccount);
        return newDebitAccount;
    }

    @Override
    public Account createCreditAccount(Client client, BigDecimal openingBalance) {
        Account newCreditAccount = new CreditAccount(client, this, openingBalance, clock);
        client.addAccount(newCreditAccount);
        accounts.add(newCreditAccount);
        return newCreditAccount;
    }

    @Override
    public Account createDepositAccount(Client client, BigDecimal openingBalance, Date dueTo) {
        Account newDepositAccount = new DepositAccount(client, this, openingBalance, clock, dueTo);
        client.addAccount(newDepositAccount);
        accounts.add(newDepositAccount);
        return newDepositAccount;
    }

    @Override
    public void changeDebitInterest(BigDecimal newInterest) {
        debitInterest.changeInterest(newInterest);
        notify("Debit interest changed to " + newInterest);
    }

    @Override
    public void changeDepositInterests(List<DepositInterest> newDepositInterests) {
        depositInterests = newDepositInterests;
        notify("Deposit interests changed");
    }

    @Override
    public void changeCreditLimit(BigDecimal newCreditLimit) {
        creditConditions.changeCreditLimit(newCreditLimit);
        notify("Credit limit changed to " + newCreditLimit);
    }

    @Override
    public void changeCreditFee(BigDecimal newCreditFee) {
        creditConditions.changeCreditFee(newCreditFee);
        notify("Credit fee changed to " + newCreditFee);
    }

    @Override
    public void changeSuspiciousClientLimit(BigDecimal newLimit) {
        suspiciousClientLimit.changeLimit(newLimit);
        notify("Limit for suspicious clients changed to " + newLimit);
    }

    @Override
    public void changeClock(Clock newClock) {
        Objects.requireNonNull(newClock);
        clock = newClock;
    }

    @Override
    public Client getClient(String name, String surname) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);

        Client client = null;
        for (Client c : clients) {
            if (c.getName().equals(name) && c.getSurname().equals(surname)) {
                client = c;
                break;
            }
        }

        if (client == null) {
            throw BankException.noSuchClientException(name, surname);
        }

        return client;
    }

    @Override
    public Account getAccount(String id) throws BanksException {
        Account account = null;
        for (Account a : accounts) {
            if (a.getId().toString().equals(id)) {
                account = a;
                break;
            }
        }

        if (account == null) {
            throw BankException.noSuchAccountException();
        }

        return account;
    }

    @Override
    public void attach(BankObserver bankObserver) throws BanksException {
        Objects.requireNonNull(bankObserver);
        if (observers.contains(bankObserver)) {
            throw BankException.observerIsAlreadySubscribed(bankObserver.getId());
        }
    }

    @Override
    public void detach(BankObserver bankObserver) throws BanksException {
        Objects.requireNonNull(bankObserver);
        if (!observers.remove(bankObserver)) {
            throw BankException.noSuchObserverException(bankObserver.getId());
        }
    }

    @Override
    public void notify(String message) {
        for (BankObserver observer : observers) {
            observer.update(message);
        }
    }
}
