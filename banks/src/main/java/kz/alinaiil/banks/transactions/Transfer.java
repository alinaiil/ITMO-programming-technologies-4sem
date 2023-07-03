package kz.alinaiil.banks.transactions;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.models.TransactionStatus;

import java.math.BigDecimal;

/**
 * Transaction of transferring
 * @author alinaiil
 * @version 1.0
 */
public class Transfer implements Transaction {
    private final Account sender;
    private final Account receiver;
    private final BigDecimal amount;
    private TransactionStatus status;
    private BigDecimal fee;

    /**
     * Creates the transaction and changes its status to InProgress
     * @param accountSender Account to send the transfer
     * @param accountReceiver Account to receive the transfer
     * @param amount Amount to be transferred
     */
    public Transfer(Account accountSender, Account accountReceiver, BigDecimal amount) {
        status = TransactionStatus.InProcess;
        this.sender = accountSender;
        this.receiver = accountReceiver;
        this.amount = amount;
    }

    /**
     * Executes transaction
     */
    @Override
    public void execute() {
        sender.withdrawMoney(amount);
        receiver.replenishAccount(amount);
        fee = sender.fixedFee();
        status = TransactionStatus.Completed;
    }

    /**
     * Cancels the transactions
     * @return false - if transaction was cancelled, false - if transaction could not be cancelled
     */
    @Override
    public boolean cancel() {
        if (status == TransactionStatus.Completed) {
            receiver.withdrawMoney(amount);
            sender.replenishAccount(amount.add(fee));
            status = TransactionStatus.Cancelled;
            return true;
        }

        return false;
    }
}
