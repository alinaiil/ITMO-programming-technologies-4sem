package kz.alinaiil.banks.exceptions;

import java.util.UUID;

/**
 * Exception class for the transactions
 * @author alinaiil
 * @version 1.0
 */
public class BanksTransactionException extends BanksException{
    private BanksTransactionException(String message) {
        super(message);
    }

    public static BanksTransactionException negativeAmountException(UUID id)
    {
        return new BanksTransactionException("Cannot execute transaction with id " + id + ". Given money amount is negative");
    }

    public static BanksTransactionException uncompletedTransactionCancellation(UUID id)
    {
        return new BanksTransactionException("Cannot cancel the transaction with id " + id + ". Transaction must be completed to be cancelled");
    }

    public static BanksTransactionException noTransactionsWithSuchIdCancellingException(UUID id)
    {
        return new BanksTransactionException("Cannot cancel transaction with id " + id + ". There is no transactions with such id");
    }

    public static BanksTransactionException transactionIsNullException()
    {
        return new BanksTransactionException("Transaction is null! Set the transaction");
    }
}
