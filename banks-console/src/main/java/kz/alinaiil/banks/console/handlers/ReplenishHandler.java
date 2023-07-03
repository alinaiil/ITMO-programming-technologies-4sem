package kz.alinaiil.banks.console.handlers;

import kz.alinaiil.banks.entities.accounts.Account;
import kz.alinaiil.banks.interfaces.CentralBank;
import kz.alinaiil.banks.transactions.Transaction;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public class ReplenishHandler extends TransactionHandler {
    @Override
    public UUID handle(CentralBank centralBank, Account account, BigDecimal amount, String request, Scanner in) {
        if (request.equals("Replenish")) {
            return centralBank.replenishAccount(account, amount);
        } else {
            return super.handle(centralBank, account, amount, request, in);
        }
    }
}
