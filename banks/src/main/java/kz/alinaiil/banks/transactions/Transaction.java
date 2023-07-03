package kz.alinaiil.banks.transactions;

/**
 * Lists methods for transactions
 * @author alinaiil
 * @version 1.0
 */
public interface Transaction {
    /**
     * Executes transaction
     */
    void execute();

    /**
     * Cancels the transactions
     * @return true - if transaction was cancelled, false - if transaction could not be cancelled
     */
    boolean cancel();
}
