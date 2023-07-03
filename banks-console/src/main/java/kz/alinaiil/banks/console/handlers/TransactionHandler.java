package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.Bank;
import kz.alinaiil.banks.interfaces.CentralBank;
import kz.alinaiil.banks.interfaces.Client;
import kz.alinaiil.banks.transactions.Transaction;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public abstract class TransactionHandler {
    private TransactionHandler next;

    public TransactionHandler setNext(TransactionHandler handler) {
        next = handler;
        return handler;
    }

    public UUID handle(CentralBank centralBank, Account account, BigDecimal amount, String request, Scanner in) {
        return next.handle(centralBank, account, amount, request, in);
    }
}
