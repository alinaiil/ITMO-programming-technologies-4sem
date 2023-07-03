package kz.alinaiil.banks.transactions;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.models.TransactionStatus;

import java.math.BigDecimal;

/**
 * Transaction of withdrawal
 * @author alinaiil
 * @version 1.0
 */
public class Withdraw implements Transaction {
    private final Account account;
    private final BigDecimal amount;
    private TransactionStatus status;
    private BigDecimal fee;

    /**
     * Creates the transaction and changes its status to InProgress
     * @param account Account for withdrawal
     * @param amount Amount of withdrawal
     */
    public Withdraw(Account account, BigDecimal amount) {
        status = TransactionStatus.InProcess;
        this.account = account;
        this.amount = amount;
    }

    /**
     * Executes transaction
     */
    @Override
    public void execute() {
        account.withdrawMoney(amount);
        fee = account.fixedFee();
        status = TransactionStatus.Completed;
    }

    /**
     * Cancels the transactions
     * @return false - if transaction was cancelled, false - if transaction could not be cancelled
     */
    @Override
    public boolean cancel() {
        if (status == TransactionStatus.Completed) {
            account.replenishAccount(amount.add(fee));
            status = TransactionStatus.Cancelled;
            return true;
        }

        return false;
    }
}
