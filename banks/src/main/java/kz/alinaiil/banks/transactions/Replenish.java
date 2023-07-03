package kz.alinaiil.banks.transactions;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.models.TransactionStatus;

import java.math.BigDecimal;

/**
 * Transaction of replenishment
 * @author alinaiil
 * @version 1.0
 */
public class Replenish implements Transaction {
    private final Account account;
    private final BigDecimal amount;
    private TransactionStatus status;

    /**
     * Creates the transaction and changes its status to InProgress
     * @param account Account for replenishment
     * @param amount Amount of replenishment
     */
    public Replenish(Account account, BigDecimal amount) {
        status = TransactionStatus.InProcess;
        this.account = account;
        this.amount = amount;
    }

    /**
     * Executes transaction
     */
    @Override
    public void execute() {
        account.replenishAccount(amount);
        status = TransactionStatus.Completed;
    }

    /**
     * Cancels the transactions
     * @return false - if transaction was cancelled, false - if transaction could not be cancelled
     */
    @Override
    public boolean cancel() {
        if (status == TransactionStatus.Completed) {
            account.withdrawMoney(amount);
            status = TransactionStatus.Cancelled;
            return true;
        }

        return false;
    }
}
