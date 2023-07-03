package kz.alinaiil.banks.transactions;

import kz.alinaiil.banks.exceptions.BanksException;
import kz.alinaiil.banks.exceptions.BanksTransactionException;

import java.util.HashMap;
import java.util.UUID;

/**
 * Works with transactions, regulates their executing and keeps transaction history
 * @author alinaiil
 * @version 1.0
 */
public class TransactionInvoker {
    private final HashMap<UUID, Transaction> transactionHistory = new HashMap<>();
    private Transaction transaction = null;

    /**
     * Sets transaction and registers their id
     * @param transaction Transaction to be set
     * @return Transaction's id
     */
    public UUID setTransaction(Transaction transaction) {
        this.transaction = transaction;
        UUID transactionId = UUID.randomUUID();
        this.transactionHistory.put(transactionId, transaction);
        return transactionId;
    }

    /**
     * Executes set transaction
     * @throws BanksException Throws exception if transaction isn't set
     */
    public void executeTransaction() throws BanksException {
        if (transaction == null) {
            throw BanksTransactionException.transactionIsNullException();
        }

        transaction.execute();
    }

    /**
     * Cancels transaction by its id
     * @param id id of the transaction to be cancelled
     * @throws BanksException Throws exception if there's no such transactions or the transaction was not completed
     */
    public void cancelTransaction(UUID id) throws BanksException {
        if (!transactionHistory.containsKey(id)) {
            throw BanksTransactionException.noTransactionsWithSuchIdCancellingException(id);
        }

        if (!transactionHistory.get(id).cancel()) {
            throw BanksTransactionException.uncompletedTransactionCancellation(id);
        }

        transactionHistory.remove(id);
    }
}
