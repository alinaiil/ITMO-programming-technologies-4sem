package kz.alinaiil.banks.entities.accounts;

import kz.alinaiil.banks.clocks.Clock;
import kz.alinaiil.banks.exceptions.AccountException;
import kz.alinaiil.banks.exceptions.BanksException;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.Client;
import kz.alinaiil.banks.observers.ClockObserver;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Class for the deposit account
 * @author alinaiil
 * @version 1.0
 */
@Getter
@Setter
public class DepositAccount implements Account, ClockObserver {
    private final BigDecimal openingBalance;
    private final Clock clock;
    private final Date endOfTerm;
    private BigDecimal interest = BigDecimal.valueOf(0);
    private Date previousCheckDay;
    private int checkedDaysCounter = 0;
    private String accountType = "Deposit";

    /**
     * Creates deposit account
     * @param client Client to whom this account belongs
     * @param bank Bank in which this account is opened
     * @param openingBalance Balance with which the account is opened
     * @param clock Clock for this account
     * @param dueTo Deposit is unreachable due this date
     */
    public DepositAccount(Client client, Bank bank, BigDecimal openingBalance, Clock clock, Date dueTo) {
        checkAmount(openingBalance);
        accountClient = client;
        accountBank = bank;
        this.openingBalance = openingBalance;
        balance = openingBalance;
        this.clock = clock;
        this.clock.attach(this);
        previousCheckDay = Date.from(Calendar.getInstance().toInstant());
        endOfTerm = dueTo;
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
        return BigDecimal.valueOf(0);
    }

    @Override
    public void withdrawMoney(BigDecimal amount) throws BanksException {
        checkAmount(amount);

        if (!checkTermEnded()) {
            throw AccountException.depositTermHasNotEndedYetException();
        }

        if (isSuspicious() && amount.compareTo(accountBank.getSuspiciousClientLimit().limit) > 0) {
            throw AccountException.suspiciousClientWithdrawException(amount, accountBank.getId());
        }

        if (balance.subtract(amount).compareTo(BigDecimal.valueOf(0)) < 0) {
            throw AccountException.debitAccountNotEnoughMoneyException(balance, amount);
        }

        balance = balance.subtract(amount.add(amount));
    }

    @Override
    public void replenishAccount(BigDecimal amount) {
        checkAmount(amount);
        balance = balance.add(amount);
    }

    /**
     * Adds commission if the term hasn't ended yet
     */
    public void addCommission() {
        if (!checkTermEnded()) {
            BigDecimal dailyInterest = accountBank.getDebitInterest().interest.divide(BigDecimal.valueOf(36500), 10, RoundingMode.CEILING);
            interest = interest.add(balance.multiply(dailyInterest));
        }
    }

    /**
     * Accrues commission and sets its value to 0 for a new period
     */
    public void accrueCommission() {
        balance = balance.add(interest);
        interest = BigDecimal.valueOf(0);
    }

    @Override
    public void update(Date currentTime) {
        long daysUnchecked = TimeUnit.DAYS.convert(Math.abs(currentTime.getTime() - previousCheckDay.getTime()), TimeUnit.MILLISECONDS);
        daysUnchecked += 1;
        while (daysUnchecked > 0)
        {
            addCommission();
            checkedDaysCounter++;

            if (checkedDaysCounter == 30)
            {
                accrueCommission();
                checkedDaysCounter = 0;
            }

            daysUnchecked--;
        }

        previousCheckDay = currentTime;
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

    /**
     * Checks if the term had already ended
     * @return True - term has ended, false - term has not ended yet
     */
    private boolean checkTermEnded() {
        return clock.getCurrentTime().after(endOfTerm);
    }
}
